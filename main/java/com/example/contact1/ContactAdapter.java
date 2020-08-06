package com.example.contact1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.contact1.TabhostActivity.arrayContact;

public class ContactAdapter extends BaseAdapter {

    private Context context;
    private int layout ;
    private List<Contact> contactList;

    public ContactAdapter(Context context, int layout, List<Contact> contactList) {  // nhận mảng dữ liệu
        this.context = context;
        this.layout = layout;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgImage,imgGoi,imgNhantin;
        TextView tvName,tvNumber;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view =  LayoutInflater.from(context).inflate(layout,parent,false);
            holder.imgImage= view.findViewById(R.id.img_image);
            holder.tvName= view.findViewById(R.id.tv_name);
            holder.tvNumber= view.findViewById(R.id.tv_number);
            holder.imgGoi= view.findViewById(R.id.img_goi);
            holder.imgNhantin=view.findViewById(R.id.img_nhantin);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
         Contact contact = contactList.get(position);// lấy từng contact  trong  danh sách contact
        holder.tvName.setText(contact.getTen());
        holder.tvNumber.setText(contact.getSo());
        byte[] hinhanh = contact.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);//chuyển hình về như cũ
        holder.imgImage.setImageBitmap(bitmap);

       holder.imgGoi.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
                intentgoidien(position);
            }
        });  // nút gọi

        holder.imgNhantin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentnhantin(position);
            }
        });//nút  nhắn tin
        return view;

    }
    public void intentnhantin(int position){   // nhắn tin
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:"+arrayContact.get(position).getSo()));
       context.startActivity(intent);
    }
    public void intentgoidien(int position){   //gọi điện
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+arrayContact.get(position).getSo()));
        context.startActivity(intent);

    }}



