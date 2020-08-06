package com.example.contact1;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;




public class TabhostActivity extends AppCompatActivity{

    public static DataBase database;
    public static DataBase2 dataBase2;
    public static ArrayList<Contact> arrayContact;
    public static ArrayList<Contact> arrayContact2;
    public ContactAdapter adapter;

    public ListView lvDanhSachContact;

    public ContactAdapter customAdapter;
    public ListView lvDanhSachYeuthich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost);

        lvDanhSachContact = findViewById(R.id.lv_danhsachcontact);
        lvDanhSachYeuthich = findViewById(R.id.lv_danhsachyeuthich);

        arrayContact = new ArrayList<>();
        adapter = new ContactAdapter(this, R.layout.item_contact, arrayContact); //chuyển mảng  qua cho  item
        lvDanhSachContact.setAdapter(adapter);   //  cài lên lisview

        arrayContact2 = new ArrayList<>();
        customAdapter = new ContactAdapter(this,R.layout.item_contact,arrayContact2);
        lvDanhSachYeuthich.setAdapter(customAdapter);


        lvDanhSachContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Showdialog(position);
                return false;
            }
        });

        lvDanhSachYeuthich.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = arrayContact2.get(position);
                SQLiteDatabase database = dataBase2.getWritableDatabase();
                database.delete("Contacts2", " Id = " + contact.getId(), null);  // xóa  một đối tượng dựa vào id
                getDataContact2();
            return false;
            }
        });

        Tabhost();
        getDataContact();
        getDataContact2();
        checkAndRequestPermissions();
    }


    public void getDataContact() {
        database = new DataBase(this, "QuanLy.sqlite", null, 1);  // khởi tạo bảng dữ liệu  vs các thông số   ten , so  hinh ảnh
        database.QueryData("CREATE TABLE IF NOT EXISTS Contacts(Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten TEXT, So TEXT, HinhAnh BLOB )");

        arrayContact.clear();  // xóa  dữ liệu

        Cursor cursor = database.GetData("SELECT * FROM Contacts");    //lấy dữ liệu  ra bằng phương thức GetData
        while (cursor.moveToNext()) {
            arrayContact.add(new Contact(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3)
            ));
        }
        adapter.notifyDataSetChanged();

    }


    public void getDataContact2(){
        dataBase2 = new DataBase2(TabhostActivity.this, "QuanLy2.sqlite", null, 1);
        dataBase2.QueryData2("CREATE TABLE IF NOT EXISTS Contacts2(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Number TEXT, Image BLOB )");

         arrayContact2.clear();

         Cursor cursor2 = dataBase2.GetData2("SELECT * FROM Contacts2");
         while (cursor2.moveToNext()) {
           arrayContact2.add(new Contact(
                 cursor2.getInt(0),
                 cursor2.getString(1),
                 cursor2.getString(2),
                   cursor2.getBlob(3)
         ));
          }
         customAdapter.notifyDataSetChanged();

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

    private void Tabhost() {
        TabHost tabHost =findViewById(R.id.tabHost);
        tabHost.setup();   //cai dặt tabhost
        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("Danh Bạ");
        tab1.setContent(R.id.danhba);
        tabHost.addTab(tab1);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("Yêu thích");
        tab2.setContent(R.id.yeuthich);
        tabHost.addTab(tab2);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  //khoi tạo menu
        getMenuInflater().inflate(R.menu.add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.menutao){                          //  băt sự kiện menu
            startActivity(new Intent(TabhostActivity.this,addcontactActivity.class));
        }if (item.getItemId()== R.id.menuyeuthich){
            startActivity(new Intent(TabhostActivity.this,DanhsachActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }


     public  void Showdialog(final int position){
        final Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.showdialog);
        dialog.setCancelable(true);
        Button btnXoa = dialog.findViewById(R.id.btn_xoa);
        Button btnSua =dialog.findViewById(R.id.btn_sua);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = arrayContact.get(position);
                SQLiteDatabase database = TabhostActivity.database.getWritableDatabase();
                database.delete("Contacts"," Id = " + contact.getId(),null);// xóa dựa trên  id
                getDataContact();
                 dialog.dismiss();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = arrayContact.get(position);
                Intent intent = new Intent(TabhostActivity.this, chinhsuaContactActivity.class);
                intent.putExtra("ID",contact.getId());         ///vận chuyển   dữ liệu sang chinhsuaContactActivity
                intent.putExtra("NAME",contact.getTen());
                intent.putExtra("NUMBER",contact.getSo());
                intent.putExtra("IMAGE",contact.getHinh());
                startActivity(intent);


            }
        });
        dialog.show();
     }


}

