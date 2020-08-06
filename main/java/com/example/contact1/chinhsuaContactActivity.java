package com.example.contact1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import static android.os.Build.ID;
import static junit.runner.Version.id;

public class chinhsuaContactActivity extends AppCompatActivity {

 public  ImageView imgH;
 public  EditText edtT,edtS;
 public  Button btnC,btnH;
    int REQUEST_CODE_FILE =1232;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinhsua_contact);
        anhxa();

        Intent intent = getIntent();
        edtT.setText(intent.getStringExtra("NAME"));
        edtS.setText(intent.getStringExtra("NUMBER"));  //nhận dữ liệu  từ tabhostActivity
            Bitmap bitmap = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("IMAGE"),0,intent.getByteArrayExtra("IMAGE").length);
      imgH.setImageBitmap(bitmap);///  chuyển  hình  từ mảng byte về  như cũ



    btnC.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {  // nút update

            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgH.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,5, byteArray);  // chuyển hinh sảng kieu mảng byte
            byte[] hinh = byteArray.toByteArray();

            String ten = edtT.getText().toString().trim();
            String so = edtS.getText().toString().trim();
            Intent intent = getIntent();
            int id = 0;

                SQLiteDatabase database = TabhostActivity.database.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("Ten",ten);
                values.put("So",so);
                values.put("HinhAnh",hinh);///  update    dữ liệu mới  vào
                database.update("Contacts", values," Id = " + intent.getIntExtra("ID",id),null );

            Toast.makeText(chinhsuaContactActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(chinhsuaContactActivity.this,TabhostActivity.class));

        }
    });
    btnH.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {  // nút hủy
            startActivity(new Intent(chinhsuaContactActivity.this,TabhostActivity.class));
        }
    });

    imgH.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {    // nút  hình
            Intent intent  = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,REQUEST_CODE_FILE);
        }
    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_FILE && resultCode == RESULT_OK && data != null){
            Uri uri  =data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);  //mở đường dẫn  tới ảnh
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgH.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void anhxa() {
        imgH = findViewById(R.id.img_h);
        edtT=findViewById(R.id.edt_t);
        edtS=findViewById(R.id.edt_s);
        btnC=findViewById(R.id.btn_c);
        btnH=findViewById(R.id.btn_h);
    }

}

