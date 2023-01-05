package com.example.tp1partie2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivity extends AppCompatActivity {

    String numberChallenge1;
    String numberChallenge2;
    TextView textViewChallenge1;
    TextView textViewChallenge2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                numberChallenge1= null;
                numberChallenge2= null;
            } else {
                numberChallenge1= extras.getString("Num1");
                numberChallenge2= extras.getString("Num2");
            }
        } else {
            numberChallenge1= (String) savedInstanceState.getSerializable("Num1");
            numberChallenge2= (String) savedInstanceState.getSerializable("Num2");
        }

        textViewChallenge1 = (TextView) findViewById(R.id.textViewChallenge1);
        textViewChallenge2 = (TextView) findViewById(R.id.textViewChallenge2);

        textViewChallenge1.setText(numberChallenge1);
        textViewChallenge2.setText(numberChallenge2);
    }

    public void finish(View view){
        this.onBackPressed();
        Toast.makeText(getApplicationContext(),"l’opération a été annulée",Toast.LENGTH_SHORT).show();
    }

    public void onClickOK(View view){
        EditText editTextSum;

        Intent myIntent = new Intent(this, MainActivity.class);
        editTextSum = (EditText) findViewById(R.id.editTextNumberSum);

        myIntent.putExtra("Sum", String.valueOf(editTextSum.getText()));

        startActivity(myIntent);
    }
}