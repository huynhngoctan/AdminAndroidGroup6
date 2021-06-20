package com.example.adminandroidgroup6.manageContact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FireBaseHelperContact;
import com.example.adminandroidgroup6.model.Contact;
import com.example.adminandroidgroup6.model.User;
import com.example.adminandroidgroup6.support.LoadingDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManageContactDetailActivity extends AppCompatActivity {
 Toolbar toolbar;
 CircleImageView ivAvatar;
 EditText edtUserName,edtPhone,edtCreateDate,edtStatus,edtTypeContact,edtContentContact;
 Button btnDelete,btnReply;
 User user;
 Contact contact;
 LoadingDialog loadingDialog;
 int REQUEST_CODE_SMS=1;

 String phone;
 String replyContent;

 //Firebase
    FireBaseHelperContact helper;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_contact_detail);

        toolbar =findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionDelete();
            }
        });
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDialog();
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

    public void init(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        user  = (User) bundle.getSerializable("user");
        contact = (Contact) bundle.getSerializable("contact");
        loadingDialog = new LoadingDialog(this);

        ivAvatar = findViewById(R.id.imageViewAvatarActivityManageContactDetail);
        edtUserName = findViewById(R.id.editTextUserNameActivityManageContactDetail);
        edtPhone = findViewById(R.id.editTextPhoneActivityManageContactDetail);
        edtCreateDate =findViewById(R.id.editTextCreateDateActivityManageContactDetail);
        edtStatus = findViewById(R.id.editTextStatusActivityManageContactDetail);
        edtTypeContact = findViewById(R.id.editTextTypeContactActivityManageContactDetail);
        edtContentContact = findViewById(R.id.editTextContentActivityManageContactDetail);
        btnDelete =findViewById(R.id.buttonDeleteContactActivityManageContactDetail);
        btnReply = findViewById(R.id.buttonReplyContactActivityManageContactDetail);

        if(user.getLinkImage()!=null)
            Picasso.with(this).load(user.getLinkImage()).fit().centerCrop().into(ivAvatar);
        edtUserName.setText(user.getUsername());
        edtPhone.setText(user.getPhone());
        edtCreateDate.setText(contact.getDateCreate());
        edtStatus.setText(contact.getStatus());
        edtTypeContact.setText(contact.getType());
        edtContentContact.setText(contact.getContent());

        db= FirebaseDatabase.getInstance().getReference();
        helper = new FireBaseHelperContact(this,db);
    }
    public void actionDelete(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo!");
        alertDialog.setMessage("Bạn có chắc là muốn xóa liên hệ này không");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadingDialog.startLoadingDialog();
                deleteInDB();
                loadingDialog.dismissDialog();
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
    public void deleteInDB(){
        if(helper.delete(contact.getIdContact())) {
            Toast.makeText(ManageContactDetailActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
        else Toast.makeText(ManageContactDetailActivity.this,"Xóa thất bại",Toast.LENGTH_SHORT).show();
    }
    public void sendDialog(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_send_sms);

        EditText edtPhone = dialog.findViewById(R.id.editTextPhoneDialogSendSMS);
        EditText edtContentReply = dialog.findViewById(R.id.editTextReplyContentDialogSendSMS);
        Button btnSend = dialog.findViewById(R.id.buttonSendDialogSendSMS);
        Button btnCancel = dialog.findViewById(R.id.buttonCancelDialogSendSMS);

        edtPhone.setText(user.getPhone());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtContentReply.getText().toString().isEmpty()){
                    Toast.makeText(ManageContactDetailActivity.this,"Vui lòng nhập nội dung phản hồi",Toast.LENGTH_SHORT).show();
                }else {
                    replyContent = edtContentReply.getText().toString().trim();
                    ActivityCompat.requestPermissions(ManageContactDetailActivity.this,
                            new String[]{Manifest.permission.SEND_SMS},REQUEST_CODE_SMS);
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(Toolbar.LayoutParams.FILL_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        if(requestCode==REQUEST_CODE_SMS&&grantResults.length>0&&grantResults[0]==getPackageManager().PERMISSION_GRANTED){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(user.getPhone(),null,replyContent,null,null);
            Toast.makeText(this,"Gửi thành công",Toast.LENGTH_SHORT).show();
            helper.updateStatus(user.getIdUser(),"Đã xử lý");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}