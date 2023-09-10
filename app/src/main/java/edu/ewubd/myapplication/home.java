package edu.ewubd.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {

    Button btnAddExpense, btnViewExpense, btnShowProfile, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnAddExpense = findViewById(R.id.btn_add_expense);
        btnViewExpense = findViewById(R.id.btn_view_expenses);
        btnShowProfile = findViewById(R.id.btnProfile);
        btnExit = findViewById(R.id.btnExit);

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, AddExpense.class);
                startActivity(intent);
            }
        });

        btnViewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, ViewExpense.class);
                startActivity(intent);
            }
        });

        btnShowProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Profile.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    }
}
