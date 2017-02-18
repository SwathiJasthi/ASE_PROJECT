package tutorial.cs5551.com.homeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

    }


    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

        builder.setTitle("***REALLY EXIT***");

        // builder.setIcon(R.drawable.win);

        builder.setMessage("Do you want exit?").setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg0) {
                        Toast.makeText( HomeActivity.this,"You clicked on Yes", Toast.LENGTH_SHORT).show();
                        finish();              //Intent i = new Intent(MainActivity.this, MainActivity.class);

                    }
                })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int arg0) {
                                Toast.makeText(HomeActivity.this, "You clicked on No", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();   /* Intent i = new Intent(MainActivity.this, DiceCrap.class);
                                        startActivity(i);*/

                            }
                        });

        AlertDialog alertdialog = builder.create();
        Window window = alertdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        alertdialog.show();
    }

}
