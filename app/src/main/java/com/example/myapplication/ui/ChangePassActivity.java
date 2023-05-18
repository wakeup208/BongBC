package com.example.myapplication.ui;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.UserSQL;

public class ChangePassActivity extends AppCompatActivity {

    private EditText edtNewPass, edtPass, edtConfirmPass;
    private Button btnSave, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        
        edtNewPass = findViewById(R.id.edt_new_password);
        edtPass = findViewById(R.id.edt_password);
        edtConfirmPass = findViewById(R.id.edt_confirm_password);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        edtPass.setTransformationMethod(new PasswordTransformationMethod());
        edtConfirmPass.setTransformationMethod(new PasswordTransformationMethod());
        edtNewPass.setTransformationMethod(new PasswordTransformationMethod());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = edtPass.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String cfPass = edtConfirmPass.getText().toString();
                if(pass.isEmpty() || newPass.isEmpty() || cfPass.isEmpty()){
                    Toast.makeText(getBaseContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                else if(pass.compareTo(MainActivity.account.getPassword())!= 0){
                    Toast.makeText(getBaseContext(), "Sai mật khẩu cũ!", Toast.LENGTH_SHORT).show();
                }
                else if(newPass.compareTo(cfPass) != 0){
                    Toast.makeText(getBaseContext(), "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                }
                else{
                    UserSQL sql = new UserSQL(getBaseContext());
                    MainActivity.account.setPassword(newPass);
                    sql.updateAccount(MainActivity.account);
                    Toast.makeText(getBaseContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}