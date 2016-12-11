var express = require( 'express' );
var app = express();

app.get( '/', function ( req, res ) {
  res.send( 'Merhaba' );
} );

app.listen( 3000, function () {
  console.log( 'Sunucu baslatildi...' );
} );
