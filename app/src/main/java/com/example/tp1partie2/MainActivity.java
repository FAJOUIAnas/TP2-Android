package com.example.tp1partie2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
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

    EditText inputChallenge1;
    EditText inputChallenge2;
    String sum;

    int numChallenge1;
    int numChallenge2;
    int sumInt;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                sum = null;
            } else {
                sum= extras.getString("SUM");
            }
        } else {
            sum = (String) savedInstanceState.getSerializable("SUM");
        }
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

    public void searchUrl(){
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

    public void checkActivity(View view) {

        Intent intent = new Intent(this, CheckActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        inputChallenge1 = findViewById(R.id.editTextNumberChallenge1);
        inputChallenge2 = findViewById(R.id.editTextNumberChallenge2);

        intent.putExtra("Num1", String.valueOf(inputChallenge1.getText()));
        intent.putExtra("Num2", String.valueOf(inputChallenge2.getText()));

        if (inputChallenge1.getText().toString().matches("") || inputChallenge2.getText().toString().matches("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Tous les champs doivent etre remplis !",
                    Toast.LENGTH_SHORT);
            toast.show();
        } else {
            someActivityResultLauncher.launch(intent);
            numChallenge1 = Integer.parseInt(String.valueOf(inputChallenge1.getText()));
            numChallenge2 = Integer.parseInt(String.valueOf(inputChallenge2.getText()));
        }
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == 78) {
                    // There are no request codes
                    Intent data = result.getData();
                    if( data != null){
                        sum = data.getStringExtra("SUM");

                        sumInt= Integer.parseInt(sum);

                        if(numChallenge1+numChallenge2 == sumInt){
                            searchUrl();
                        }
                    }
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "l’opération a été annulée",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
}