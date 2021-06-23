package com.example.adminandroidgroup6.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adminandroidgroup6.MainActivity;
import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FireBaseHelperUser;
import com.example.adminandroidgroup6.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName;
    EditText edtPass;
    DatabaseReference db;
    FireBaseHelperUser helper;
    Button btnLogin;
    CheckBox cbSavePass;
    SharedPreferences sharedPreferences;
    long backPressedTime;
    Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseDatabase.getInstance().getReference();
        helper = new FireBaseHelperUser(this, db);

        edtUserName = findViewById(R.id.editTextUserLoginActivityLogin);
        edtPass = findViewById(R.id.editTextPassLoginActivityLogin);
        btnLogin = findViewById(R.id.buttonLoginActivityLogin);
        cbSavePass = findViewById(R.id.checkBoxSavePassACtivityLogin);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        edtUserName.setText(sharedPreferences.getString("username",""));
        edtPass.setText(sharedPreferences.getString("pass",""));
        if(sharedPreferences.getBoolean("isLogin",false)) cbSavePass.setChecked(true);
        else cbSavePass.setChecked(false);

//        User user = new User("00002","52hz blue whale",2000,"hntan2000@gmail.com","0848107178",
//                "Men","30/2/2021","31/2/2021","tan123","tannotgay","Admin","gagaga","Đang sử dụng");
//        db.child("User").child("00002").setValue(user);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helper.isLoadDone())
                checkLogin();
                else Toast.makeText(LoginActivity.this,"Đang tải dữ liệu, vui lòng thử lại sau",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkLogin() {
        String userName = edtUserName.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        if (helper.checkLogin(userName, pass)) {
            if(cbSavePass.isChecked()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin", true);
                editor.putString("username", userName);
                editor.putString("pass", pass);
                editor.commit();
            }else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin", false);
                editor.putString("username", "");
                editor.putString("pass", "");
                editor.commit();
            }

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @Override
    public void onBackPressed() {
        if(backPressedTime+2000>System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else {
            backToast = Toast.makeText(this,"Chạm lại để thoát",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}