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


public class LoginActivity extends Activity {

    Button login, btnRegisterScreen;
    EditText mail,password;
    String mail_string,password_string;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(projemiz.facebookpaylasim.R.layout.login_activity);

        btnRegisterScreen = (Button)findViewById(projemiz.facebookpaylasim.R.id.btnRegisterScreen);
        btnRegisterScreen.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();



        if(preferences.getBoolean("login", false)){
            Intent i = new Intent(getApplicationContext(),HomepageActivity.class);
            startActivity(i);
            finish();
        }

        mail = (EditText)findViewById(projemiz.facebookpaylasim.R.id.mail);
        password = (EditText)findViewById(projemiz.facebookpaylasim.R.id.password);

        btnRegisterScreen = (Button) findViewById(projemiz.facebookpaylasim.R.id.btnRegisterScreen);
        btnRegisterScreen.setOnClickListener(new View.OnClickListener() { //registry screen opens !

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        login = (Button) findViewById(projemiz.facebookpaylasim.R.id.login);
        login.setOnClickListener(new View.OnClickListener() { //Login button!

            @Override
            public void onClick(View v) {
                mail_string = mail.getText().toString();
                password_string = password.getText().toString();

                if (mail_string.matches("") || password_string.matches("")) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                    alertDialog.setTitle("Uyarı");
                    alertDialog.setMessage("Eksiksiz Doldurunuz!");
                    alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                } else {

                    String email = preferences.getString("email", "");
                    String pass = preferences.getString("pass", "");

                    if (email.matches(mail_string) && pass.matches(password_string)) {

                        editor.putBoolean("login", true);
                        Intent i = new Intent(getApplicationContext(), HomepageActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                        alertDialog.setTitle("Oops!");
                        alertDialog.setMessage("Mail veya Şifre Yanlış!");
                        alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();
                    }
                }
            }
        });

    }
}
