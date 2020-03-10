package com.nikitolch.mathgamedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class GameOptionsActivity extends AppCompatActivity {

    CheckBox additionCheckBox, subtractionCheckBox, multiplicationCheckBox, divisionCheckBox;
    ArrayList<String> typeSelected = new ArrayList<String>();

    // if checkbox state changes, if  type is in typeselected, remove; if not, add it

    public void gameTypeChecked (View view) {
        boolean isTypeChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.additionCheckBox:
                if (isTypeChecked) {
                    typeSelected.add("addition");
                } else {
                    typeSelected.remove("addition");
                }
                break;
            case R.id.substractionCheckBox:
                if (isTypeChecked) {
                    typeSelected.add("subtraction");
                } else {
                    typeSelected.remove("subtraction");
                }
                break;
            case R.id.multiplicationCheckBox:
                if (isTypeChecked) {
                    typeSelected.add("multiplication");
                } else {
                    typeSelected.remove("multiplication");
                }
                break;
            case R.id.divisionCheckBox:
                if (isTypeChecked) {
                    typeSelected.add("division");
                } else {
                    typeSelected.remove("division");
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_options);

        additionCheckBox = findViewById(R.id.additionCheckBox);
        subtractionCheckBox = findViewById(R.id.substractionCheckBox);
        multiplicationCheckBox = findViewById(R.id.multiplicationCheckBox);
        divisionCheckBox = findViewById(R.id.divisionCheckBox);
    }

    // Pass selected game options to MainActivity
    public void submitGameOptions (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("options", typeSelected);
        startActivity(intent);
    }
}
