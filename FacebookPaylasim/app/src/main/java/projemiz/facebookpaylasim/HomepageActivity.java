package projemiz.facebookpaylasim;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Calendar;

public class HomepageActivity extends Activity {

    private int SELECT_FILE=1,REQUEST_CAMERA=0;
    String imgPrefix = "paylasim";
    private ImageView ivImage;
    Button settings,kamera,galeri;
    Uri imageuri;
    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(projemiz.facebookpaylasim.R.layout.homepage_activity);


        settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomepageActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

        kamera=(Button)findViewById(R.id.camera);
        kamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent kamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File dir=
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

                imageFile=new File(dir, "CameraContentDemo.jpeg");
                kamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));


                startActivityForResult(kamera,33);
            }
        });

        galeri=(Button)findViewById(R.id.gallery);
        galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);


                startActivityForResult(intent,44);
            }
        });

            }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        ImageView imageview=(ImageView)findViewById(R.id.imageView);

        if(requestCode == 33 && resultCode==RESULT_OK) {    // camera
            imageview.setImageURI(Uri.fromFile(imageFile));
            Intent i = new Intent(HomepageActivity.this, ShareActivity.class);
            i.putExtra("imagefile",imageFile.toString());
            startActivity(i);

        }else if(requestCode == 44 && resultCode==RESULT_OK) { // gallery
            Uri selectedImage = data.getData();
            Intent i = new Intent(HomepageActivity.this, ShareActivity.class);
            i.putExtra("imageuri",selectedImage.toString());
            startActivity(i);
//            imageview.setImageURI(selectedImage);
        }
        super.onActivityResult(requestCode,resultCode,data);
    }


    }



