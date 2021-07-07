package com.example.adminandroidgroup6.menuFoods;

import android.app.Dialog;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FirebaseHelper;
import com.example.adminandroidgroup6.model.Food;
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
import java.util.ArrayList;
import java.util.Calendar;

public class AddFoodActivity extends AppCompatActivity {
    Toolbar toolbar;
    RadioButton rbtnFood, rbtnTopping;
    EditText edtFoodName, edtPrice, edtDescription;
    TextView tvChooseType;
    ArrayList<String> listType;
    ArrayAdapter adapter;
    ImageView ivFood;
    Bitmap selectImage;
    int REQUEST_CODE = 123;
    Button btnSave, btnDelete;
    Switch switchStatus;
    String action;
    boolean isNewImage;
    String idFood;
    LoadingDialog loadingDialog;

    //FireBase
    DatabaseReference db;
    FirebaseHelper helper;
    String linkImage;
    String imageName;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Thêm thức ăn hoặc topping");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingDialog=new LoadingDialog(this);

        //Firebase
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        listType = new ArrayList<>();
        addListType();
        adapter = new ArrayAdapter(AddFoodActivity.this, android.R.layout.simple_list_item_1, listType);
        tvChooseType = findViewById(R.id.textViewChooseTypeFoodActivityAddFood);
        ivFood = findViewById(R.id.imageViewFoodActivityAddFood);
        btnSave = findViewById(R.id.buttonSaveActivityAddFood);
        btnDelete = findViewById(R.id.buttonDeleteActivityAddFood);
        edtFoodName = findViewById(R.id.editTextFoodNameActivityAddFood);
        edtPrice = findViewById(R.id.editTextPriceActivityAddFood);
        edtDescription = findViewById(R.id.editTextDescriptionActivityAddFood);
        rbtnFood = findViewById(R.id.radioButtonFoodActivityAddFood);
        rbtnTopping = findViewById(R.id.radioButtonToppingActivityAddFood);
        switchStatus = findViewById(R.id.switchStatusActivityAddFood);

        actionIntent();

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
                if (!checkInput()) {
                    Toast.makeText(AddFoodActivity.this, "Vui lòng nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    loadingDialog.startLoadingDialog();
                    saveToDatabase();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                deleteFood();
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
            isNewImage=true;
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
        listType.add("CAPHE");
        listType.add("TRASUA");
        listType.add("PHO");
        listType.add("COM");
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
        if (edtDescription.getText().toString().isEmpty()) return false;

        return true;
    }

    public void saveToDatabase() {
        if(isNewImage) {
            Calendar calendar = Calendar.getInstance();
            imageName = "food" + calendar.getTimeInMillis();
            StorageReference mountainsRef = storageRef.child("food" + calendar.getTimeInMillis());
            // Get the data from an ImageView as bytes

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(AddFoodActivity.this, "Lỗi upload ảnh", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            linkImage = "" + uri;
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
        else addFood();
    }

    public void addFood() {
        String foodName = edtFoodName.getText().toString().trim();
        String type = tvChooseType.getText().toString().trim();
        int price = Integer.parseInt(edtPrice.getText().toString().trim());
        String status = switchStatus.isChecked() ? "Đang mở bán" : "Không mở bán";
        String description = edtDescription.getText().toString().trim();
        boolean isTopping = rbtnFood.isChecked() ? false : true;
        Food food = new Food(foodName, type, price, status, linkImage, description, isTopping);
        if(action.equals("add")) {
            if (helper.save(food))
                Toast.makeText(AddFoodActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            else Toast.makeText(AddFoodActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
        if(action.equals("update")){
            food.setId(idFood);
            if (helper.update(food))
                Toast.makeText(AddFoodActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            else Toast.makeText(AddFoodActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
        loadingDialog.dismissDialog();
    }
    public void deleteFood(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo!");
        alertDialog.setMessage("Bạn có chắc là muốn xóa món này không");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteInDB();

            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadingDialog.dismissDialog();
            }
        });
        alertDialog.show();
    }
    public void actionIntent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String action = bundle.getString("action");
        switch (action) {
            case "update":
                btnDelete.setVisibility(View.VISIBLE);
                this.action = "update";
                Food food = (Food) bundle.getSerializable("food");
                idFood= food.getId();
                if(food.getLinkImage()!=null)
                    Picasso.with(AddFoodActivity.this).load(food.getLinkImage()).fit().centerCrop()
                            .into(ivFood);
                if (food.isTopping()) {
                    rbtnTopping.setChecked(true);
                } else {
                    rbtnFood.setChecked(true);
                }
                edtFoodName.setText(food.getFoodName());
                edtFoodName.setSelection(edtFoodName.getText().length());
                edtPrice.setText("" + food.getPrice());
                edtPrice.setSelection(edtPrice.getText().length());
                edtDescription.setText(food.getDescription());
                edtDescription.setSelection(edtDescription.getText().length());
                tvChooseType.setText(food.getType());
                if (food.getStatus().equals("Đang mở bán")) switchStatus.setChecked(true);
                else switchStatus.setChecked(false);
                linkImage=food.getLinkImage();
                break;
            case "add":
                btnDelete.setVisibility(View.INVISIBLE);
                this.action = "add";
                break;
        }
    }
    public void deleteInDB(){
        if (helper.delete(idFood)){
            if(linkImage!=null) {
                System.out.println(linkImage);
                storage.getReferenceFromUrl(linkImage).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddFoodActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            Toast.makeText(AddFoodActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
        else Toast.makeText(AddFoodActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        loadingDialog.dismissDialog();
    }
}