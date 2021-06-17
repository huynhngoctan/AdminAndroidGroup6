package com.example.adminandroidgroup6.changeInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FireBaseHelperUser;
import com.example.adminandroidgroup6.database.FirebaseHelper;
import com.example.adminandroidgroup6.menuFoods.AddFoodActivity;
import com.example.adminandroidgroup6.model.User;
import com.example.adminandroidgroup6.model.UserLogin;
import com.example.adminandroidgroup6.support.LoadingDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeInfoActivity extends AppCompatActivity {
    EditText edtName,edtBirth,edtEmail,edtPhone,edtUserName,edtPass;
    RadioButton rdbtnMale;
    Button btnUpdate;
    CircleImageView imageViewAvatar;
    UserLogin userLogin;
    User user;
    Toolbar toolbar;
    final int REQUEST_CODE=100;
    boolean isNewImage;
    Bitmap selectImage;
    LoadingDialog loadingDialog;


    //Firebase
    DatabaseReference db;
    FireBaseHelperUser helper;
    String linkImage;
    String imageName;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        init();


        //Firebase
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FireBaseHelperUser(this,db);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        loadingDialog = new LoadingDialog(this);

        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkInput()){
                    Toast.makeText(ChangeInfoActivity.this,"Vui lòng nhập đầy đủ dữ liệu",Toast.LENGTH_SHORT).show();
                }else if(!user.getUsername().equals(edtUserName.getText().toString().trim())&&
                        !helper.checkUserName(edtUserName.getText().toString().trim())){
                    Toast.makeText(ChangeInfoActivity.this,"Tên đăng nhập đã tồn tại",Toast.LENGTH_SHORT).show();
                }else { loadingDialog.startLoadingDialog();saveToDatabase();}
            }
        });
    }
    public void init(){
        userLogin = UserLogin.getInstance();
        user = userLogin.getUser();
        linkImage=user.getLinkImage();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Thay đổi thông tin cá nhân");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtName = findViewById(R.id.editTextNameActivityChangeInfo);
        edtBirth = findViewById(R.id.editTextBirthActivityChangeInfo);
        edtEmail = findViewById(R.id.editTextEmailActivityChangeInfo);
        edtPhone = findViewById(R.id.editTextPhoneActivityChangeInfo);
        edtUserName = findViewById(R.id.editTextUserNameActivityChangeInfo);
        edtPass = findViewById(R.id.editTextPassActivityChangeInfo);
        rdbtnMale = findViewById(R.id.radioButtonMaleActivityChangeInfo);
        btnUpdate = findViewById(R.id.buttonUpdateActivityChangeInfo);
        imageViewAvatar = findViewById(R.id.imageViewAvatarActivityChangeInfo);

        edtName.setText(user.getName());
        edtBirth.setText(""+user.getYearOfBirth());
        edtEmail.setText(user.getEmail());
        edtPhone.setText(user.getPhone());
        edtUserName.setText(user.getUsername());
        edtPass.setText(user.getPassword());
        if (user.getGender().equals("Nam")) {
            rdbtnMale.setChecked(true);
        } else {
            rdbtnMale.setChecked(false);
        }
        if (user.getLinkImage() != null)
            Picasso.with(this).load(user.getLinkImage()).fit().centerCrop()
                    .into(imageViewAvatar);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && data != null) {
            selectImage = loadFromUri(data.getData());
            imageViewAvatar.setImageBitmap(selectImage);
            isNewImage=true;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            if (Build.VERSION.SDK_INT > 27) {
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    public boolean checkInput(){
        if(edtName.getText().toString().isEmpty()) return false;
        if(edtBirth.getText().toString().isEmpty()) return false;
        if(edtEmail.getText().toString().isEmpty()) return false;
        if(edtPhone.getText().toString().isEmpty()) return false;
        if(edtUserName.getText().toString().isEmpty()) return false;
        if(edtPass.getText().toString().isEmpty()) return false;
        return true;
    }
    public void saveToDatabase(){
        if(isNewImage) {
            Calendar calendar = Calendar.getInstance();
            imageName = "avatar" + calendar.getTimeInMillis();
            StorageReference mountainsRef = storageRef.child("avatar" + calendar.getTimeInMillis());
            // Get the data from an ImageView as bytes

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(ChangeInfoActivity.this, "Lỗi upload ảnh", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            storage.getReferenceFromUrl(linkImage).delete();
                            linkImage = "" + uri;
                            updateUser();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
            });
        }
        else updateUser();
    }
    public void updateUser(){
        String id = user.getIdUser();
        String name = edtName.getText().toString().trim();
        long birth = Long.parseLong(edtBirth.getText().toString().trim());
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String gender = rdbtnMale.isChecked()?"Nam":"Nữ";
        String firstLogin=user.getFirstLogin();
        String lastLogin = user.getLastLogin();
        String username = edtUserName.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String role = user.getRole();
        String status = user.getStatus();


        User user = new User(id,name,birth,email,phone,gender,firstLogin,lastLogin,username,pass,role,linkImage,status);
        userLogin.setUser(user);

        if(helper.update(user))Toast.makeText(this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
        loadingDialog.dismissDialog();
    }
}