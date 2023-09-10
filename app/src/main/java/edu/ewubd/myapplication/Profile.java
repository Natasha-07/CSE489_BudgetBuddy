package edu.ewubd.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Color;


import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class Profile extends AppCompatActivity {
    private EditText etname, email;
    private TextView userid, textViewExceededBudgetValue, textViewBudgetValue;
    private Button buttonBack;
    private SharedPreferences sharedPreferences;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etname = findViewById(R.id.etname);
        email = findViewById(R.id.email);
        textViewBudgetValue = findViewById(R.id.textViewBudgetValue);
        userid = findViewById(R.id.userid);
        textViewExceededBudgetValue = findViewById(R.id.textViewExceededBudgetValue);
        buttonBack = findViewById(R.id.buttonBack);

        dbHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        //user data from shared preferences
        String name = sharedPreferences.getString("userName", null);
        String emailStr = sharedPreferences.getString("userEmail",null);
        String userId = sharedPreferences.getString("userId", null);
        //database
        double income = dbHelper.getIncome();

        etname.setText(name);
        email.setText(emailStr);
        textViewBudgetValue.setText(String.valueOf(income));
        textViewBudgetValue.setEnabled(false); //disabled
        userid.setText(userId);

        //expense amount from shared preferences
        int monthlyExpenses = dbHelper.getSumOfExpensesForMonth(Calendar.getInstance().get(Calendar.MONTH));
        String monthName = new DateFormatSymbols().getMonths()[Calendar.getInstance().get(Calendar.MONTH)];
        textViewExceededBudgetValue.setText(String.format(Locale.getDefault(), "%s: %d tk", monthName, monthlyExpenses));

        double budgetDifference = income - monthlyExpenses;

        //update budget difference text view
        if (budgetDifference >= 0) {
            textViewExceededBudgetValue.setTextColor(Color.GREEN);
            textViewExceededBudgetValue.setText(String.format(Locale.getDefault(),"In Budget %.2f", budgetDifference));
        } else {
            textViewExceededBudgetValue.setTextColor(Color.RED);
            textViewExceededBudgetValue.setText(String.format(Locale.getDefault(),"Over Budget %.2f", budgetDifference));
        }

        db = new DatabaseHelper(this).getWritableDatabase();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, home.class));
                finish();
            }
        });
    }
}
