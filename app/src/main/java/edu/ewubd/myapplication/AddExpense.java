package edu.ewubd.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddExpense extends AppCompatActivity {

    private EditText editIncome, editExpenseAmount, editExpenseDesc;
    private Spinner spinnerCategories;
    private Button buttonSave, buttonBack;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private TextView selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        editIncome = findViewById(R.id.edit_text_income);
        editExpenseAmount = findViewById(R.id.edit_text_expense_amount);
        editExpenseDesc = findViewById(R.id.edit_text_expense_desc);
        spinnerCategories = findViewById(R.id.spinner_categories);
        buttonSave = findViewById(R.id.buttonSave);
        buttonBack = findViewById(R.id.buttonBack);
        selectedCategory = findViewById(R.id.selected_category);

        //loads category names from  strings.xml
        String[] categories = new String[] {
                getString(R.string.category_food),
                getString(R.string.category_transportation),
                getString(R.string.category_entertainment),
                getString(R.string.category_shopping),
                getString(R.string.category_utilities),
                getString(R.string.category_housing),
                getString(R.string.category_health),
                getString(R.string.category_education),
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategories.setAdapter(adapter);

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                selectedCategory.setText(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double income = Double.parseDouble(editIncome.getText().toString());
                double expenseAmount = Double.parseDouble(editExpenseAmount.getText().toString());
                //System.out.println("Exxxpnese"+ expenseAmount);
                String category = spinnerCategories.getSelectedItem().toString();
                String expenseDesc = editExpenseDesc.getText().toString();

                DatabaseHelper databaseHelper = new DatabaseHelper(AddExpense.this);
                long result = databaseHelper.insertData(income, expenseAmount, category, expenseDesc);

                if (result == -1) {
                    Toast.makeText(AddExpense.this, "Error: Unable to save expense", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddExpense.this, "Expense saved successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddExpense.this, home.class));
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddExpense.this, home.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //device back button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddExpense.this, home.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();

    }
}
