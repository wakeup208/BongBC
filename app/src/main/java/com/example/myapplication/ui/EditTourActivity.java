package com.example.myapplication.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.db.UserSQL;
import com.example.myapplication.model.Tour;
import com.example.myapplication.model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class EditTourActivity extends AppCompatActivity {

    Tour tour;
    EditText edtName, edtStartDate, edtTrans, edtDuration, edtTotal;
    TextView txtStartDate;
    Button btnSave, btnCancel;
    RadioGroup radioGroup;
    RadioButton btnPlane, btnTrain;
    ImageButton btnDate;
    Spinner spPosition;
    Uri uri;
    ImageView employImage;
    ImageView cameraImage;
    FrameLayout employView;
    int SELECT_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touredit);
        tour = (Tour) getIntent().getSerializableExtra("tour");
        init();

        //employImage.setImageURI(Uri.parse(user.getImage()));
        edtName.setText(tour.getName());
        txtStartDate.setText(tour.getStartDate());
        if (tour.getTrans().compareToIgnoreCase("plane") == 0) btnPlane.setChecked(true);
        else btnTrain.setChecked(true);
        edtTotal.setText(tour.getTotal());
        edtDuration.setText(tour.getDuration());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String date = txtStartDate.getText().toString();
                int tmp = radioGroup.getCheckedRadioButtonId();
                String image = uri != null ? uri.toString() : tour.getImg();
                String duration = edtDuration.getText().toString();
                String total = edtTotal.getText().toString();
                String trans = "";
                if (tmp == btnPlane.getId()) trans = "plane";
                else trans = "female";
                if (trans.isEmpty() || name.isEmpty() || image.isEmpty() || duration.isEmpty() || total.isEmpty() || date.compareToIgnoreCase("dd/MM/yyyy") == 0) {
                    Toast.makeText(getBaseContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        UserSQL helper = new UserSQL(getBaseContext());
                        tour.setName(name);
                        tour.setStartDate(date);
                        tour.setTrans(trans);
                        tour.setDuration(duration);
                        tour.setImg(image);
                        tour.setTotal(total);
                        helper.updateTour(tour);
                        Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (NumberFormatException ignored) {

                    }

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtStartDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTourActivity.this, R.style.DialogTheme,
                        dateSetListener, year, month, day);

                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FF1E2F97"));
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF1E2F97"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                employImage.setImageBitmap(bitmap);
                cameraImage.setVisibility(View.GONE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {
        edtName = findViewById(R.id.edt_name);
        edtDuration = findViewById(R.id.edt_duration);
        edtTotal = findViewById(R.id.edt_total);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDate = findViewById(R.id.btn_birth);
        radioGroup = findViewById(R.id.btn_group);
        spPosition = findViewById(R.id.sp_position);
        txtStartDate = findViewById(R.id.txt_birth);
        btnTrain = findViewById(R.id.radio_train);
        btnPlane = findViewById(R.id.radio_plane);
        employImage = findViewById(R.id.employ_image);
        cameraImage = findViewById(R.id.camera);
        employView = findViewById(R.id.image_picker);
        employView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, SELECT_PHOTO);
        });
    }
}