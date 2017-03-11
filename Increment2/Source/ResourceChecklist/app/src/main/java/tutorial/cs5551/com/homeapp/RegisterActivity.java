package tutorial.cs5551.com.homeapp;

/**
 * Created by Divya Gaddam on 2/22/2017.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class RegisterActivity extends AppCompatActivity{

    TextView errorText;
    String userName;
    String password;
    String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        //b=(Button)findViewById(R.id.btnSelectPhoto);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds options to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    public boolean validate() {
        boolean valid = true;

        if (userName.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
            Toast.makeText(getApplicationContext(), "Enter a valid email address", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            errorText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            Toast.makeText(getApplicationContext(), "Password should be atleast 4-6 alphanumeric", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            errorText.setError(null);
        }
        if (confirmPassword.isEmpty() || confirmPassword.length() < 4 || confirmPassword.length() > 10) {
            Toast.makeText(getApplicationContext(), "Password should be atleast 4-6 alphanumeric", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            errorText.setError(null);
        }

        if(!password.equals(confirmPassword)){
            valid=false;
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        else {
            errorText.setError(null);
        }

        return valid;
    }
    public void newUser(View v) {

        EditText usernameCtrl = (EditText) findViewById(R.id.txt_email);
        EditText passwordCtrl = (EditText) findViewById(R.id.txt_Pwd);
        EditText confirmPasswordCtrl = (EditText) findViewById(R.id.txt_ConfirmPwd);

        errorText = (TextView) findViewById(R.id.sign_error);
        userName = usernameCtrl.getText().toString();
        password = passwordCtrl.getText().toString();
        confirmPassword = confirmPasswordCtrl.getText().toString();
        if (validate()) {
            Intent goTonextPage = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(goTonextPage);
        }
    }


    public void onClickButton(View v) {
        //This code redirects to the photo activity.
        Intent redirect = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(redirect);
    }
}
