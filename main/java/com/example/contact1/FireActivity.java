package com.example.contact1;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FireActivity extends AppCompatActivity {

    private EditText edtEmail, email;
    private EditText edtPassword, password;
    private Button btnLogin, btnSignup;
    private TextView tvInfor;
    private int counter = 5;
    private CheckBox cbGhinho;


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup);
        tvInfor = findViewById(R.id.tv_infor);
        cbGhinho = findViewById(R.id.cb_ghinho);
        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);    //lấy dữ liệu ra
        cbGhinho.setChecked(sharedPreferences.getBoolean("checked", false));
        btnSignup.setEnabled(sharedPreferences.getBoolean("enable",true));  // ẩn nút
        if (cbGhinho.isChecked()) {
            edtEmail.setText(sharedPreferences.getString("email", ""));
            edtPassword.setText(sharedPreferences.getString("pass", ""));
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {// hai edit
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
                String E = sharedPreferences.getString("email", "");  //lấy  dữ liệu  trong  sharedPreferences
                String P = sharedPreferences.getString("pass", "");
                String email = edtEmail.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                if ((email.equals(E)) && (pass.equals(P))) {    //  so sánh   dữ liệu
                    Intent call = new Intent(FireActivity.this,TabhostActivity.class);// đúng
                    startActivity(call);
                } else {
                    counter--;
                    Toast.makeText(FireActivity.this, " Password or Name of you the wrong , please input again", Toast.LENGTH_SHORT).show();
                    tvInfor.setText("No of attempts remaining: " + String.valueOf(counter));       // sai
                    if (counter == 0) {
                        btnLogin.setEnabled(false);
                    }
                  }      //hộp checkbox
                if(email.equals(E) && pass.equals(P)){                  //so sánh dữ liệu
                    if (cbGhinho.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("checked",true);   //chèn   checked = true
                        editor.apply();
                    }else{// sai
                        SharedPreferences.Editor editor =sharedPreferences.edit();
                        editor.remove("checked"); // xóa cheked
                        editor.apply();}
                }
            } });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
    }

    public void showdialog() {  //  đăng kí  tài khoản
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        email = dialog.findViewById(R.id.edt_name);
        password = dialog.findViewById(R.id.edt_pass);
        Button Yes = dialog.findViewById(R.id.btn_yes);
        Button No = dialog.findViewById(R.id.btn_no);
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();
                SharedPreferences.Editor editor = sharedPreferences.edit();//  chèn dữ liệu  vào sharedPreferences
                editor.putString("email", e);
                editor.putString("pass", p);
                editor.putBoolean("enable",false);
                editor.apply();
                btnSignup.setEnabled(false);
                dialog.cancel();
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
