package edu.ewubd.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenses.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "expenses";
    private static final String COL_ID = "_id";
    private static final String COL_INCOME = "income";
    private static final String COL_EXPENSE_AMOUNT = "expense_amount";
    private static final String COL_CATEGORY = "category";
    private static final String COL_EXPENSE_DESC = "expense_desc";
    private static final String COL_DATE = "date";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_INCOME + " REAL," +
                    COL_EXPENSE_AMOUNT + " REAL," +
                    COL_CATEGORY + " TEXT," +
                    COL_EXPENSE_DESC + " TEXT," +
                    COL_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertData(double income, double expenseAmount, String category, String expenseDesc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_INCOME, income);
        contentValues.put(COL_EXPENSE_AMOUNT, expenseAmount);
        contentValues.put(COL_CATEGORY, category);
        contentValues.put(COL_EXPENSE_DESC, expenseDesc);

        return db.insert(TABLE_NAME, null, contentValues);
    }

    public int getSumOfExpensesForMonth(int month) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_EXPENSE_AMOUNT + ") FROM " + TABLE_NAME + " WHERE strftime('%m', " + COL_DATE + ") = ?", new String[]{String.format("%02d", month + 1)});
        int sum = 0;
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        return sum;
    }

    public int getSumOfExpensesForCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_EXPENSE_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COL_CATEGORY + " = ?", new String[]{category});
        int sum = 0;
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        return sum;
    }

    public double getIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT (" + COL_INCOME + ") FROM " + TABLE_NAME, null);
        double income = 0;
        if (cursor.moveToFirst()) {
            income = cursor.getDouble(0);
        }
        cursor.close();
        return income;
    }
}

