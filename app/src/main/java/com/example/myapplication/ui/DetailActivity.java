package com.example.myapplication.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.UserSQL;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.User;

public class DetailActivity extends AppCompatActivity {
    User user;
    Account account;
    ImageButton btnBack;
    TextView txtName, txtBirth, txtGender, txtRole;
    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        user = (User) getIntent().getSerializableExtra("user");
        account = (Account) getIntent().getSerializableExtra("account");
        if (user != null) {
            txtName.setText("Họ tên: " + user.getName());
            txtBirth.setText("Ngày sinh: " + user.getBirthday());
            if (user.getGender().compareToIgnoreCase("nam") == 0)
                txtGender.setText("Giới tính: Nam");
            else txtGender.setText("Giới tính: Nữ");
            if (user.getImage() != null)
                im.setImageURI(Uri.parse(user.getImage()));
            else
                im.setImageDrawable(getApplication().getResources().getDrawable(R.drawable.im_male));
            if (user.getName().equalsIgnoreCase("atc") == false) {
                txtRole.setText("Chức vụ: Customer");
            } else txtRole.setText("Chức vụ: Admin");

        } else {
            UserSQL userSQL = new UserSQL(getBaseContext());
            User user1 = userSQL.getOne(account.getId());
            txtName.setText("Họ tên: " + user1.getName());
            txtBirth.setText("Ngày sinh: " + user1.getBirthday());
            if (user1.getGender().compareToIgnoreCase("nam") == 0)
                txtGender.setText("Giới tính: Nam");
            else txtGender.setText("Giới tính: Nữ");
            if (user1.getImage() != null)
                im.setImageURI(Uri.parse(user1.getImage()));
            else
                im.setImageDrawable(getApplication().getResources().getDrawable(R.drawable.im_male));
            if (user1.getName().equalsIgnoreCase("atc") == false) {
                txtRole.setText("Chức vụ: Customer");
            } else txtRole.setText("Chức vụ: Admin");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void init() {
        btnBack = findViewById(R.id.btn_back);
        txtName = findViewById(R.id.name_employ);
        txtBirth = findViewById(R.id.birth_employ);
        txtGender = findViewById(R.id.gender_employ);
        txtRole = findViewById(R.id.position_employ);
        im = findViewById(R.id.im_employ);
    }


}
