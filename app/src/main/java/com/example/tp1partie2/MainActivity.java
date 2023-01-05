package com.example.tp1partie2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static boolean result=false;
    private int CALL_Perm;

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.i("LIFECYCLE ", getLocalClassName() + " : ici onActivityResult");

                    if (result.getResultCode()== 78){
                        Intent intent = result.getData();

                        if (intent!=null){
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                                    {Manifest.permission.CALL_PHONE}, CALL_Perm);
                            EditText txt = findViewById(R.id.editTxtPhone);
                            String number = txt.getText().toString();
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)));
                        }
                    }
                }
            });

    String numberSum;
    TextView textViewSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                numberSum= null;
            } else {
                numberSum= extras.getString("Sum");
            }
        } else {
            numberSum= (String) savedInstanceState.getSerializable("Sum");
        }

        /*textViewChallenge1 = (TextView) findViewById(R.id.textViewChallenge1);

        textViewChallenge1.setText(numberChallenge1);*/
    }

    public void callNumber(View view){
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        activityLauncher.launch(myIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_Perm) {
            //the array is empty if not granted
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "GRANTED CALL",Toast.LENGTH_SHORT).show();
        }
    }

    public void searchUrl(View view){
        EditText url = findViewById(R.id.edtTxtUrl);
        String urlName = url.getText().toString();
        if (urlName.isEmpty()) {
                urlName = "https://www.emi.ac.ma/";
        }
        if (!urlName.startsWith("http://") && !urlName.startsWith("https://")){
            urlName = "http://" + urlName;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlName));
        startActivity(browserIntent);
    }

    public void persoActivity(View view){
        Intent myIntent = new Intent(this, PersoActivity.class);
        startActivity(myIntent);
    }

    public void checkActivity(View view){
        EditText editTextChallenge1;
        EditText editTextChallenge2;

        Intent myIntent = new Intent(this, CheckActivity.class);
        editTextChallenge1 = (EditText) findViewById(R.id.editTextNumberChallenge1);
        editTextChallenge2 = (EditText) findViewById(R.id.editTextNumberChallenge2);

        myIntent.putExtra("Num1", String.valueOf(editTextChallenge1.getText()));
        myIntent.putExtra("Num2", String.valueOf(editTextChallenge2.getText()));

        startActivity(myIntent);
    }
}