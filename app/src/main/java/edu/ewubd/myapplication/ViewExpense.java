package edu.ewubd.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class ViewExpense extends AppCompatActivity {

    private TextView tvMonthlyExpensesValue, tvCategory1Value, tvCategory2Value, tvCategory3Value, tvIncome;
    private Button buttonBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);

        tvMonthlyExpensesValue = findViewById(R.id.tvMonthlyExpensesValue);
        tvCategory1Value = findViewById(R.id.tvCategory1Value);
        tvCategory2Value = findViewById(R.id.tvCategory2Value);
        tvCategory3Value = findViewById(R.id.tvCategory3Value);
        tvIncome = findViewById(R.id.tvIncome);
        buttonBack = findViewById(R.id.buttonBack);

        dbHelper = new DatabaseHelper(this);

        //monthly expenses value calculate
        int monthlyExpenses = dbHelper.getSumOfExpensesForMonth(Calendar.getInstance().get(Calendar.MONTH));
        String monthName = new DateFormatSymbols().getMonths()[Calendar.getInstance().get(Calendar.MONTH)];
        tvMonthlyExpensesValue.setText(String.format(Locale.getDefault(), "%s: %d tk", monthName, monthlyExpenses));

        //category-wise spending
        tvCategory1Value.setText(String.format(Locale.getDefault(), "Food: %d tk", dbHelper.getSumOfExpensesForCategory("Food")));
        tvCategory2Value.setText(String.format(Locale.getDefault(), "Transportation: %d tk", dbHelper.getSumOfExpensesForCategory("Transportation")));
        tvCategory3Value.setText(String.format(Locale.getDefault(), "Shopping: %d tk", dbHelper.getSumOfExpensesForCategory("Shopping")));

        //income vs. expenses
        double totalIncome = dbHelper.getIncome();
        int totalExpenses = dbHelper.getSumOfExpensesForMonth(Calendar.getInstance().get(Calendar.MONTH));

        System.out.println("income"+ totalIncome);
        System.out.println("expense"+ totalExpenses);
        tvIncome.setText(String.format(Locale.getDefault(),"%.2f tk VS %d tk", totalIncome, totalExpenses));


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewExpense.this, home.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewExpense.this, home.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
