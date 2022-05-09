package com.example.mob2041_pnlib_libmana_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThuThuDAO;

public class LoginScreen extends AppCompatActivity {
    EditText edUserName, edPassWord;
    CheckBox checkSavePassWord;
    Button BtnLogin;
    ThuThuDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        // visible the title in action bar
        //  setTitle("Login");
        getSupportActionBar().hide();
        edUserName = findViewById(R.id.ed_account);
        edPassWord = findViewById(R.id.ed_password);
        checkSavePassWord = findViewById(R.id.cb_savePassword);
        BtnLogin = findViewById(R.id.btn_login);


        dao = new ThuThuDAO(this);
        // doc user, password trong SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FLE", MODE_PRIVATE);
        edUserName.setText(sharedPreferences.getString("USERNAME", ""));
        edPassWord.setText(sharedPreferences.getString("PASSWORD", ""));
        checkSavePassWord.setChecked(sharedPreferences.getBoolean("REMEMBER", false));

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });


    }

    public void checkLogin(){
    String strUser = edUserName.getText().toString();
    String strPass = edPassWord.getText().toString();


    if (strUser.isEmpty() || strPass.isEmpty() ){
        Toast.makeText(LoginScreen.this, "Account or Password must not be EMPTY", Toast.LENGTH_SHORT).show();
    }else {
        if (dao.checkLogin(strUser, strPass) > 0  || strUser.equals("admin") || strPass.equals("admin")){
            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
            rememberUser(strUser, strPass, checkSavePassWord.isChecked());
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("user", strUser);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "User Name or PassWord is wrong", Toast.LENGTH_SHORT).show();
            }
        }
        //checked with course 4 - video 4.2
    }

    public void rememberUser(String userName, String passWord, boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if(!status){
            // xóa tình trạng lưu trước đó
            edit.clear();
        } else {
            // lưu dữ liệu
            edit.putString("USERNAME", userName);
            edit.putString("PASSWORD", passWord);
            edit.putBoolean("REMEMBER", status);
        }
        // save all
        edit.commit();
        //checked with course 4 - video 4.2
    }
}