package com.example.contact1;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class addcontactActivity extends AppCompatActivity {
   ImageView imgHinh;
   EditText edtTen,edtSo;
    Button btnThem,btnHuy;
    int REQUEST_CODE_FILE = 1239;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);
    anhxa();


        btnThem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable(); // lấy hình
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArray);///chuyển hình  thành mảng byte
            byte[] hinhanh = byteArray.toByteArray();

            TabhostActivity.database.INSERT_CONTACT(             // chèn xuống database
                    edtTen.getText().toString().trim(),
                    edtSo.getText().toString().trim(),
                    hinhanh
            );
            Toast.makeText(addcontactActivity.this,"Đã thêm",Toast.LENGTH_SHORT).show();
           showNotification();
            startActivity(new Intent(addcontactActivity.this,TabhostActivity.class));

        }
    });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addcontactActivity.this,TabhostActivity.class));
            }
        });


        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FILE);
            }
        });

        checkAndRequestPermissions();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_FILE && resultCode == RESULT_OK && data != null){
            Uri uri  =data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri); ///   mở đường  dẫn tới hình ảnh
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void anhxa() {
        imgHinh= findViewById(R.id.img_hinh);
        edtTen= findViewById(R.id.edt_ten);
        edtSo = findViewById(R.id.edt_so);
        btnThem=findViewById(R.id.btn_them);
        btnHuy=findViewById(R.id.btn_huy);
    }

    private void showNotification() {
        String id = "main_channel";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);// Lấy ra dịch vụ thông báo (Một dịch vụ có sẵn của hệ thống).
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importtance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(id, name, importtance);
            notificationChannel.setDescription(description);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            notificationChannel.enableVibration(false);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

         Intent intent =new Intent();
          intent.setAction(Intent.ACTION_CALL);
          intent.setData(Uri.parse("tel:"+ edtSo.getText().toString().trim()));
        PendingIntent pending = PendingIntent.getActivity(this,2,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =new NotificationCompat.Builder(this, id);
        notificationBuilder.setSmallIcon(R.drawable.ic_perm_contact_calendar_black_24dp);
        notificationBuilder.setContentTitle("Chào bạn,bạn mới vừa thêm một số liên lạc");
        notificationBuilder.setContentText("Bạn có muốn gọi ngay bây giờ không");
        notificationBuilder.setLights(Color.WHITE,500,5000);// mau trang
        notificationBuilder.setColor(Color.GREEN); //mau xanh
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);//am thanh
        notificationBuilder.addAction(R.drawable.call,"Gọi",pending);  // nút gọi
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1000,notificationBuilder.build());  // Xây dựng thông báo và gửi nó lên hệ thống.


    }
    private void checkAndRequestPermissions() {  /// xin quyền gọi nhắn tin  dành cho  android  6. trở lên
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
}
