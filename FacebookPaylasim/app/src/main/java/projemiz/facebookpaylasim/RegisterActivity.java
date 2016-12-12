package projemiz.facebookpaylasim;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends Activity {

    Button register,btnLoginScreen;
    EditText name,email,pass;
    String name_string,email_string,pass_string;


    SharedPreferences preferences;


    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(projemiz.facebookpaylasim.R.layout.register_activity);

        register = (Button) findViewById(projemiz.facebookpaylasim.R.id.register);

        btnLoginScreen = (Button)findViewById(projemiz.facebookpaylasim.R.id.btnLoginScreen);
        name = (EditText)findViewById(projemiz.facebookpaylasim.R.id.name);
        email = (EditText)findViewById(projemiz.facebookpaylasim.R.id.email);
        pass = (EditText)findViewById(projemiz.facebookpaylasim.R.id.pass);


        register.setOnClickListener(new View.OnClickListener() {//Kayıt ol butonu tıklanınca

            @Override
            public void onClick(View v) {
                name_string = name.getText().toString();
                email_string = email.getText().toString();
                pass_string = pass.getText().toString();
                if(pass_string.matches("") || email_string.matches("") || pass_string.matches("")){

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterActivity.this);
                    alertDialog.setTitle("Uyarı");
                    alertDialog.setMessage("Bilgiler Boş Geçilemez!");
                    alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

                        }
                    });
                    alertDialog.show();
                }else{

                    preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    editor = preferences.edit();

                    editor.putString("name", name_string);
                    editor.putString("email", email_string);
                    editor.putString("pass", pass_string);
                    editor.putBoolean("login", true);

                    editor.commit();

                    Intent i = new Intent(getApplicationContext(),HomepageActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });



    }
}
