package com.example.adminandroidgroup6.manageAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FireBaseHelperUser;
import com.example.adminandroidgroup6.model.User;
import com.example.adminandroidgroup6.support.LoadingDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManageUserDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtName, edtBirth, edtEmail, edtPhone, edtGender, edtUserName, edtRole;
    CircleImageView ivAvatar;
    Switch switchStatus;
    Button btnSave;
    User user;
    DatabaseReference db;
    FireBaseHelperUser helper;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getRole().equals("Admin"))
                    Toast.makeText(ManageUserDetailActivity.this,
                            "Bạn không thể khóa tài khoản này",Toast.LENGTH_SHORT).show();
                else {
                    loadingDialog.startLoadingDialog();
                    saveToDatabase();
                    loadingDialog.dismissDialog();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void init() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        loadingDialog = new LoadingDialog(this);
        edtName = findViewById(R.id.editTextNameActivityManageUserDetail);
        edtBirth = findViewById(R.id.editTextBirthActivityManageUserDetail);
        edtEmail = findViewById(R.id.editTextEmailActivityManageUserDetail);
        edtPhone = findViewById(R.id.editTextPhoneActivityManageUserDetail);
        edtGender = findViewById(R.id.editTextGenderActivityManageUserDetail);
        edtUserName =findViewById(R.id.editTextUserNameActivityManageUserDetail);
        edtRole = findViewById(R.id.editTextRoleActivityManageUserDetail);
        btnSave = findViewById(R.id.buttonSaveActivityManageUserDetail);
        ivAvatar = findViewById(R.id.imageViewAvatarActivityManageUserDetail);

        switchStatus = findViewById(R.id.switchStatusActivityManageuserDetail);

        edtName.setText(user.getName());
        edtBirth.setText("" + user.getYearOfBirth());
        edtEmail.setText(user.getEmail());
        edtPhone.setText(user.getPhone());
        edtGender.setText(user.getGender());
        edtUserName.setText(user.getUsername());
        edtRole.setText(user.getRole());

        if (user.getStatus().equals("Đang sử dụng")) switchStatus.setChecked(false);
        else switchStatus.setChecked(true);

        db = FirebaseDatabase.getInstance().getReference();
        helper = new FireBaseHelperUser(this, db);
        if(user.getLinkImage()!=null)
        Picasso.with(this).load(user.getLinkImage()).fit().centerCrop().into(ivAvatar);
    }
    public void saveToDatabase(){
        String status=switchStatus.isChecked()?"Bị khóa":"Đang sử dụng";
        if(helper.updateStatus(user.getIdUser(),status))
            Toast.makeText(ManageUserDetailActivity.this,
                    "Lưu thành công",Toast.LENGTH_SHORT).show();
        else Toast.makeText(ManageUserDetailActivity.this,
                "Lưu thất bại",Toast.LENGTH_SHORT).show();
    }
}