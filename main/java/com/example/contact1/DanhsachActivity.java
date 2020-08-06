package com.example.contact1;


import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



public class DanhsachActivity extends AppCompatActivity {

    public ContactAdapter2 adapter2;
    public ListView  lvDanhSachLuaChon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach);

        lvDanhSachLuaChon= findViewById(R.id.lv_danhsachluachon);

        adapter2 =new ContactAdapter2(this,R.layout.item_contact2,TabhostActivity.arrayContact);
        lvDanhSachLuaChon.setAdapter(adapter2);  //danh sách lựa chọn contact

        lvDanhSachLuaChon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact= TabhostActivity.arrayContact.get(position);
                 // insert  vào  bảng  Contacts2
                TabhostActivity.dataBase2.INSERT_CONTACT2(contact.getTen(),contact.getSo(),contact.getHinh());
                startActivity(new Intent(DanhsachActivity.this,TabhostActivity.class));

            }
        });

    }

}
