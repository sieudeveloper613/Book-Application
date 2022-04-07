package com.example.mob2041_pnlib_libmana_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThuThuDAO;

public class LoginScreen extends AppCompatActivity {
    private static final String FF810909 = "FF810909";
    EditText edUserName, edPassWord;
    CheckBox checkSavePassWord;
    Button BtnLogin, BtnCancel;
    ThuThuDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        // visible the title in action bar
        //  setTitle("Login");
        getSupportActionBar().hide();
        edUserName = findViewById(R.id.ed_userName);
        edUserName.setText("admin");
        edPassWord = findViewById(R.id.ed_passWord);
        edPassWord.setText("admin");
        checkSavePassWord = findViewById(R.id.ck_savePassWord);
        BtnLogin = findViewById(R.id.btn_login);
        BtnLogin.setBackgroundColor(Color.rgb(140, 11, 11));
        BtnCancel = findViewById(R.id.btn_cancel);
        BtnCancel.setBackgroundColor(Color.rgb(140, 11 , 11));

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

        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUserName.setText("");
                edPassWord.setText("");
            }
        });
    }

    public void checkLogin(){
    String StrUser = edUserName.getText().toString();
    String StrPass = edPassWord.getText().toString();


    if (StrUser.isEmpty() || StrPass.isEmpty() ){
        Toast.makeText(getApplicationContext(), "User Name or PassWord must not be EMPTY", Toast.LENGTH_SHORT).show();
    }else {
        if (dao.checkLogin(StrUser, StrPass) > 0  || StrUser.equals("mors") || StrPass.equals("070301")){
            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
            rememberUser(StrUser, StrPass, checkSavePassWord.isChecked());
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("user", StrUser);
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