package edu.ewubd.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpForm extends AppCompatActivity {
    private LinearLayout layoutName, layoutEmail, layoutPhone, layoutRPass;
    private TextView tvTitle, tvAccountInfo;
    private Button btnToggle, btnExit, btnGO;
    private EditText etName, etEmail, etPhone, etUserId, etPass, etRPass;
    private CheckBox cbUserId, cbLogin;


    Boolean flg = false;//
    Boolean wasOpened = false;

    SharedPreferences sp;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);


        layoutName = findViewById(R.id.layoutName);
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutPhone = findViewById(R.id.layoutPhone);
        layoutRPass = findViewById(R.id.layoutRPass);

        etName = (EditText)findViewById(R.id.etName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPhone = (EditText)findViewById(R.id.etPhone);
        etUserId = (EditText)findViewById(R.id.etUserId);
        etPass = (EditText)findViewById(R.id.etPass);
        etRPass = (EditText)findViewById(R.id.etRPass);

        cbUserId = (CheckBox)findViewById(R.id.cbUserId);
        cbLogin = (CheckBox)findViewById(R.id.cbLogin);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvAccountInfo = (TextView)findViewById(R.id.tvAccountInfo);

        btnToggle = (Button)findViewById(R.id.btnToggle);
        btnExit = (Button)findViewById(R.id.btnExit);
        btnGO = (Button)findViewById(R.id.btnGO);

        sp = getSharedPreferences("user_info", MODE_PRIVATE);
        myEdit = sp.edit();

        if(sp.contains("userId") && sp.contains("userPwd")){
            loginUi();
        }else userSignup();


        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUi();

            }

        });

        btnExit.setOnClickListener(view -> finish());

    }
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    private void loginUi(){
        flg = true;
        layoutName.setVisibility(View.GONE);
        layoutEmail.setVisibility(View.GONE);
        layoutPhone.setVisibility(View.GONE);
        layoutRPass.setVisibility(View.GONE);
        tvTitle.setText("Login");
        tvAccountInfo.setText("Don't have an account?");
        btnToggle.setText("Sign Up");

        if (sp.contains("userId")) {
            if(sp.getBoolean("rememberUserId",true)){
                cbUserId.setChecked(true);
                etUserId.setText(sp.getString("userId", ""));
            }
        }
        if (sp.contains("userPwd")) {
            if(sp.getBoolean("rememberlogin",true)){
                cbLogin.setChecked(true);
                etPass.setText(sp.getString("userPwd", ""));
            }
        }
        else{
            Toast.makeText(SignUpForm.this, "No user found" , Toast.LENGTH_LONG).show();
        }
        btnGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sending email and ID
                if(flg){
                    String userid_str = etUserId.getText().toString();
                    String password_str = etPass.getText().toString();

                    if(userid_str.equals(etUserId.getText().toString()) && password_str.equals(etPass.getText().toString())){
                        Intent i = new Intent(SignUpForm.this, home.class);
                        startActivity(i);
                        finish();
                    }
                    return;
                }

                String matchInfo="";

                boolean rememberUser = cbUserId.isChecked();
                boolean rememberPass = cbLogin.isChecked();

                if(sp.getString("userId","").equals(etUserId.getText().toString())){
                    if(sp.getString("userPwd","").equals(etPass.getText().toString())){

                        myEdit.putBoolean("rememberUserId", rememberUser);
                        myEdit.putBoolean("rememberlogin", rememberPass);
                        myEdit.commit();

                        Intent i = new Intent(SignUpForm.this, home.class);
                        startActivity(i);
                        finish();
                    }else{
                        matchInfo = matchInfo+"Password ";
                        etPass.setError("Wrong Password");
                    }
                }else{
                    matchInfo = matchInfo+"User ID ";
                    etUserId.setError("No user found");
                }

                if(!matchInfo.isEmpty()){
                    matchInfo = matchInfo+" does not match";
                    Toast.makeText(SignUpForm.this, matchInfo, Toast.LENGTH_LONG).show();
                }else  Toast.makeText(SignUpForm.this, "Login successful", Toast.LENGTH_LONG).show();
                flg = true;
            }
        });
    }
    private void userSignup() {

        btnGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = etName.getText().toString();
                String userEmail = etEmail.getText().toString();
                String userPhone = etPhone.getText().toString().trim();
                String userId = etUserId.getText().toString().trim();
                String userPwd = etPass.getText().toString().trim();
                String userRePwd = etRPass.getText().toString().trim();
                boolean rememberUser = cbUserId.isChecked();
                boolean rememberPass = cbLogin.isChecked();

                boolean error= false;
                if (etName.getText().toString().length()<2) {
                    etName.setError("Error in name field");
                    error = true;
                }
                else {
                    System.out.println("Name :" + etName.getText());
                }

                if (!isEmailValid(etEmail.getText().toString())) {
                    etEmail.setError("Error in name field");
                    error = true;
                }
                else {
                    System.out.println("Email :" + etEmail.getText());
                }
                if (etPhone.getText().toString().length()<10) {
                    etPhone.setError("Error in Phone field");
                    error = true;
                }
                else {
                    System.out.println("Phone :" + etPhone.getText());
                }
                if (etUserId.getText().toString().length()<2) {
                    etUserId.setError("Error in user id field");
                    error = true;
                }
                else {
                    System.out.println("User id :" + etUserId.getText());
                }
                if (etPass.getText().toString().length()<2) {
                    etPass.setError("Error in password field");
                    error = true;
                }
                if (!etPass.getText().toString().equals(etRPass.getText().toString())) {
                    etRPass.setError("Error in password field");
                    error = true;
                }
                if(!error){

                    System.out.println("user Name: " + userName);
                    System.out.println("user Email: " + userEmail);
                    System.out.println("user Phone Number: " + userPhone);
                    System.out.println("user Id: " + userId);
                    System.out.println("user pwd: " + userPwd);

                    if (userPwd.equals(userRePwd)) {
                        myEdit.putString("userName", userName);
                        myEdit.putString("userEmail", userEmail);
                        myEdit.putString("userPhone", userPhone);
                        myEdit.putString("userId", userId);
                        myEdit.putString("userPwd", userPwd);
                        myEdit.putBoolean("rememberUserId", rememberUser);
                        myEdit.putBoolean("rememberlogin", rememberPass);
                        myEdit.commit();

                        Toast.makeText(SignUpForm.this, "Data Saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpForm.this, home.class));
                        finish();
                    } else {
                        Toast.makeText(SignUpForm.this, "Invalid password!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        wasOpened = true;
    }

    @Override
    public void onStart(){
        super.onStart();
        if(wasOpened){
            finish(); //wasOpened = false
        }
    }
}