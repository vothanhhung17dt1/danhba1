package com.example.contact1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter2 extends BaseAdapter {

    private DanhsachActivity context;
    private int layout ;
    private List<Contact> contactList;

    public ContactAdapter2(DanhsachActivity context, int layout, ArrayList<Contact> contactList) {
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
        ImageView  imgImg;
        TextView  tvFullName,tvNumberPhone;
    }

    @Override
    public View getView( int position, View view, ViewGroup parent) {
       ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view =  LayoutInflater.from(context).inflate(layout,parent,false);
          holder.imgImg= view.findViewById(R.id.img_img);
          holder.tvFullName=view.findViewById(R.id.tv_fullname);
          holder.tvNumberPhone=view.findViewById(R.id.tv_numberphone);
          view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        Contact contact = contactList.get(position);
           holder.tvFullName.setText(contact.getTen());
           holder.tvNumberPhone.setText(contact.getSo());
        byte[] hinhAnh = contact.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
        holder.imgImg.setImageBitmap(bitmap);
        return view;
    }



}
