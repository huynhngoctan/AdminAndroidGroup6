package com.example.adminandroidgroup6.menuFoods;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FirebaseHelper;
import com.example.adminandroidgroup6.model.Food;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class AddFoodActivity extends AppCompatActivity {
    Toolbar toolbar;
    RadioButton rbtnFood, rbtnTopping;
    EditText edtFoodName, edtPrice;
    TextView tvChooseType;
    ArrayList<String> listType;
    ArrayAdapter adapter;
    ImageView ivFood;
    Bitmap selectImage;
    int REQUEST_CODE = 123;
    Button btnSave;
    Switch switchStatus;

    //FireBase
    DatabaseReference db;
    FirebaseHelper helper;
    String linkImage;
    String imageName;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Thêm thức ăn hoặc topping");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Firebase
        db= FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        listType = new ArrayList<>();
        addListType();
        adapter = new ArrayAdapter(AddFoodActivity.this, android.R.layout.simple_list_item_1, listType);
        tvChooseType = findViewById(R.id.textViewChooseTypeFoodActivityAddFood);
        ivFood = findViewById(R.id.imageViewFoodActivityAddFood);
        btnSave = findViewById(R.id.buttonSaveActivityAddFood);
        edtFoodName = findViewById(R.id.editTextFoodNameActivityAddFood);
        edtPrice = findViewById(R.id.editTextPriceActivityAddFood);
        rbtnFood =findViewById(R.id.radioButtonFoodActivityAddFood);
        rbtnTopping =findViewById(R.id.radioButtonToppingActivityAddFood);
        switchStatus=findViewById(R.id.switchStatusActivityAddFood);

        tvChooseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayType();
            }
        });
        ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkInput()){
                    Toast.makeText(AddFoodActivity.this,"Vui lòng nhập đủ dữ liệu",Toast.LENGTH_SHORT).show();
                }else{
                    saveToDatabase();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && data != null) {
            selectImage = loadFromUri(data.getData());
            ivFood.setImageBitmap(selectImage);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void displayType() {
        Dialog dialog = new Dialog(AddFoodActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_select_type);
        ListView lvType = dialog.findViewById(R.id.listViewTypeLayoutSelectType);
        lvType.setAdapter(adapter);
        lvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvChooseType.setText(listType.get(position));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void addListType() {
        listType.add("aaa");
        listType.add("bbb");
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

    public boolean checkInput() {
        if (edtFoodName.getText().toString().isEmpty()) return false;
        if (edtPrice.getText().toString().isEmpty()) return false;
        if (tvChooseType.getText().toString().equals("Chọn loại món  >")) return false;
        return true;
    }
public void saveToDatabase(){
    Calendar calendar = Calendar.getInstance();
    imageName = "food"+calendar.getTimeInMillis();
    StorageReference mountainsRef = storageRef.child("food"+calendar.getTimeInMillis());
    // Get the data from an ImageView as bytes

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    selectImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
    byte[] data = baos.toByteArray();

    UploadTask uploadTask = mountainsRef.putBytes(data);
    uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
            Toast.makeText(AddFoodActivity.this,"Lỗi upload ảnh",Toast.LENGTH_SHORT).show();
        }
    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            storageRef.child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    linkImage=""+uri;
                    System.out.println(linkImage);
                    addFood();
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
public void addFood(){
    String foodName =edtFoodName.getText().toString().trim();
    String type =tvChooseType.getText().toString().trim();
    double price = Double.parseDouble(edtPrice.getText().toString().trim());
    String status = switchStatus.isChecked()?"Đang mở bán":"Không mở bán";
    boolean isTopping = rbtnFood.isChecked()?false:true;
    Food food = new Food(foodName,type,price,status,linkImage,isTopping);
    if(helper.save(food)) Toast.makeText(AddFoodActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
            else Toast.makeText(AddFoodActivity.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
}
}