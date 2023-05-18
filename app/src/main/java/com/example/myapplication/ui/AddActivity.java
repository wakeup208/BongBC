package com.example.myapplication.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.UserSQL;
import com.example.myapplication.model.Tour;
import com.example.myapplication.model.User;

public class AddActivity extends AppCompatActivity {

    EditText edtName, edtDuration, edtTotal;
    TextView txtStartDate;
    Button btnAdd, btnCancel;
    RadioGroup radioGroup;
    RadioButton btnPlane, btnTrain;
    ImageButton btnDate;
    Uri uri;
    ImageView employImage;
    ImageView cameraImage;
    FrameLayout employView;
    int SELECT_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String total = edtTotal.getText().toString();
                String date = txtStartDate.getText().toString();
                String duration = edtDuration.getText().toString();
                int tmp = radioGroup.getCheckedRadioButtonId();
                String image = uri!=null ? uri.toString() : "";
                String trans = "";
                if(tmp == btnPlane.getId()) trans = "plane";
                else trans = "train";
                if (trans.isEmpty() || duration.isEmpty() || name.isEmpty() || image.isEmpty() || total.isEmpty() || date.compareToIgnoreCase("dd/MM/yyyy") == 0) {
                    Toast.makeText(getBaseContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    try{
                        UserSQL helper = new UserSQL(getBaseContext());
                        Tour tour = new Tour(name,date,trans,duration,image,total);
                        helper.addTour(tour);
                        Toast.makeText(getBaseContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }catch (NumberFormatException ex){
                        Toast.makeText(getBaseContext(), "Sai định dạng lương!", Toast.LENGTH_SHORT).show();
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
                        Log.d("hiund",dayOfMonth + "/" + (month+1 )+ "/" +year);
                        txtStartDate.setText(dayOfMonth + "/" + (month+1 )+ "/" +year );
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this,R.style.DialogTheme,
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
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                employImage.setImageBitmap(bitmap);
                cameraImage.setVisibility(View.GONE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void init(){
        edtName = findViewById(R.id.edt_name);
        edtTotal = findViewById(R.id.edt_total);
        edtDuration = findViewById(R.id.edt_duration);
        btnAdd = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDate = findViewById(R.id.btn_birth);
        radioGroup = findViewById(R.id.btn_group);
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
