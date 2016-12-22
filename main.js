var fs = require( 'fs' );

var express = require( 'express' );
var app = express();

var multer = require( 'multer' );
var upload = multer( { dest: 'uploads/' } );

var FB = require( 'fb' );
var fb = new FB.Facebook( { version: 'v2.8' } );

app.post( '/api/photo', upload.single( 'avatar' ), function ( req, res, next ) {
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

  var caption = 'from app';
  if ( req.body.caption )
    caption = req.body.caption;

  fb.api( 'me/photos', 'post',
    { source: fs.createReadStream( req.file.destination + req.file.filename ),
    caption: caption }, function ( _res ) {
    if( !_res || _res.error ) {
      res.status( 400 ).send( !_res ? 'error occurred' : _res.error );
      return;
    } res.status( 200 ).send( _res.post_id );
  } );
} );

app.listen( 3000, function () {
  console.log( 'Sunucu baslatildi...' );
} );
