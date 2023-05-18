package com.example.myapplication.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.Tour;
import com.example.myapplication.model.User;

public class DetailTourActivity extends AppCompatActivity{
    Tour tour;
    ImageButton btnBack;
    TextView txtName, txtStartDate, txtTrans, txtDuration, txtTotal;
    ImageView im;
    Button btnPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourdetail);
        init();
        String accountUser = MainActivity.account.getUsername();
        if (accountUser.equalsIgnoreCase("anhtuancao")){
            btnPlace.setVisibility(View.GONE);
        }
        tour = (Tour) getIntent().getSerializableExtra("tour");
        txtName.setText("Địa điểm: " + tour.getName());
        txtStartDate.setText("Ngày bắt đầu: "+ tour.getStartDate());
        if(tour.getTrans().compareToIgnoreCase("plane") == 0) txtTrans.setText("Phương tiện: Máy bay");
        else txtTrans.setText("Phương tiện: Tàu hỏa");
        txtDuration.setText("Thời gian: " + tour.getDuration());
        txtTotal.setText("Chi phí: " + tour.getTotal());
        im.setImageURI(Uri.parse(tour.getImg()));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Đặt thành công!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(DetailTourActivity.this, HomeFragment.class);
//                i.putExtra("account",account);
//                startActivityForResult(1);
            }
        });

    }

    public void init(){
        btnBack = findViewById(R.id.btn_back);
        txtName = findViewById(R.id.name);
        txtStartDate = findViewById(R.id.startDate);
        txtTrans = findViewById(R.id.trans);
        txtDuration = findViewById(R.id.duration);
        txtTotal = findViewById(R.id.total);
        im = findViewById(R.id.im_employ);
        btnPlace = findViewById(R.id.btn_place);
    }


}
