package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.UserSQL;
import com.example.myapplication.model.Account;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUser, edtPass, edtConfirmPass;
    private TextView login;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUser = findViewById(R.id.edt_username);
        edtPass = findViewById(R.id.edt_password);
        edtConfirmPass = findViewById(R.id.edt_confirm_password);
        login = findViewById(R.id.login);
        btnRegister = findViewById(R.id.btn_register);
        edtPass.setTransformationMethod(new PasswordTransformationMethod());
        edtConfirmPass.setTransformationMethod(new PasswordTransformationMethod());
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edtUser.getText().toString();
                String passWord = edtPass.getText().toString();
                String cfPass = edtConfirmPass.getText().toString();
                if (userName.isEmpty() || passWord.isEmpty() || cfPass.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if (passWord.compareTo(cfPass) != 0) {
                    Toast.makeText(getBaseContext(), "Xác nhận mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                } else {
                    UserSQL userSQL = new UserSQL(getBaseContext());
                    if (userSQL.checkUserName(userName)) {
                        Account account = new Account(userName, passWord);
                        int accountId = userSQL.addAccount(account);
                        Toast.makeText(getBaseContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        userSQL.test();
                        Intent i = new Intent(RegisterActivity.this, UserDetailActivity.class);
                        i.putExtra("accountId",accountId);
                        startActivityForResult(i, 1);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivityForResult(i, 1);
            }
        });
    }
}
