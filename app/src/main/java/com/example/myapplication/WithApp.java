package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.db.UserSQL;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.User;

public class WithApp extends Application {

    private boolean mc = false;
    private int a = 0;

    @Override
    public void onCreate() {
        super.onCreate();


        SharedPreferences sh = getApplicationContext().getSharedPreferences("EST", Context.MODE_PRIVATE);
        a = sh.getInt("first_time", 0);
        if (a != 111) {
            UserSQL userSQL = new UserSQL(getApplicationContext());
            Account account = new Account("anhtuancao", "123");
            int accountId = userSQL.addAccount(account);

            User user = new User("atc", "11-11-2011", "male", null, "0");
            userSQL.addUser(user, accountId);
        }
    }
}
