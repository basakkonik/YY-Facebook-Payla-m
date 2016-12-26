var fs = require( 'fs' );

var express = require( 'express' );
var app = express();

var multer = require( 'multer' );
var upload = multer( { dest: 'uploads/' } );

var FB = require( 'fb' );
var fb = new FB.Facebook( { version: 'v2.8' } );

var lastPhotoID = 0;

app.post( '/api/photo', upload.single( 'picture' ), function ( req, res, next ) {
  if ( typeof req.body !== 'undefined' ) {
    if ( !req.body.token || !req.file ) {
      res.sendStatus( 400 );
      return;
    }
  } else {
    res.sendStatus( 400 );
    return;
  }

  fb.setAccessToken( req.body.token );

  var caption = 'facebook paylasim';
  if ( req.body.caption )
    caption = req.body.caption;

  fb.api( 'me/photos', 'post',
    { source: fs.createReadStream( req.file.destination + req.file.filename ),
    caption: caption }, function ( _res ) {
    if( !_res || _res.error ) {
      res.status( 400 ).send( !_res ? 'error occurred' : _res.error );
      return;
    } else {
      getPhotoID( _res.post_id, function( err ) {
        res.status( 200 ).send( lastPhotoID );
      } );
    }
  } );
} );

app.post( '/api/tag', upload.none(), function( req, res ) {
  if ( !req.body || lastPhotoID == 0 ) {
    res.sendStatus( 400 );
    return;
  } else {
    if ( !req.body.x || !req.body.y || !req.body.tag_uid || !req.body.tag_text || !req.body.token ) {
      res.sendStatus( 400 );
    } else {
      fb.setAccessToken( req.body.token );

      fb.api( lastPhotoID + '/tags', 'post', {
        tag_text: req.body.tag_text,
        tag_uid: req.body.tag_uid,
        x: req.body.x,
        y: req.body.y
      }, function( _res ) {
        if( !_res || _res.error ) {
          res.status( 400 ).send( !_res ? 'error occurred' : _res.error );
          return;
        } else {
          res.status( 200 ).send( _res );
        }
      } );
    }
  }
} );

app.listen( 3000, function () {
  console.log( 'Sunucu baslatildi...' );
} );

function getPhotoID(postID, cb) {
  fb.api(postID, { fields:['object_id'] }, function(res){
    if(!res || res.error) {
      console.log('hata olustu');
      return;
    }
      lastPhotoID = res.object_id;
      cb();
  } );
}
