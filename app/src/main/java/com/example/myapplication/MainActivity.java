package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.db.UserSQL;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.Tour;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.CustomerFragment;
import com.example.myapplication.ui.EditTourActivity;
import com.example.myapplication.ui.HomeFragment;
import com.example.myapplication.ui.InfoFragment;
import com.example.myapplication.ui.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallback;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Account account;
    public static Tour tour;
    public static User user;
    public static String uriImage;

    BottomNavigationView bottomNavigationView;


    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    InfoFragment infoFragment = new InfoFragment();
    CustomerFragment customerFragment = new CustomerFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionX.init(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .explainReasonBeforeRequest()
                .onExplainRequestReason(new ExplainReasonCallback() {
                    @Override
                    public void onExplainReason(@NonNull ExplainScope scope, @NonNull List<String> deniedList) {
                        scope.showRequestReasonDialog(deniedList, "Đậu xanh - Rau má", "OK", "Cancel");
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback() {
                    @Override
                    public void onForwardToSettings(@NonNull ForwardScope scope, @NonNull List<String> deniedList) {
                        scope.showForwardToSettingsDialog(deniedList, "You need to allow necessary permissions in Settings manually", "OK", "Cancel");
                    }
                })
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                        if (allGranted) {
                            Toast.makeText(MainActivity.this, "All permissions are granted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show();
                        }
                    }
                });


        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        account = (Account) getIntent().getSerializableExtra("account");
        String accountName = account.getUsername();
        if (accountName.equalsIgnoreCase("anhtuancao")) {
            initListener();
        } else {
            initListener();
            bottomNavigationView.getMenu().removeItem(R.id.menu2);
            bottomNavigationView.getMenu().removeItem(R.id.menu4);
        }
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+BuildConfig.APPLICATION_ID+"/" +resourceId).toString();
    }

    private void initListener() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        setCurrentFragment(homeFragment);
//                        i.putExtra("account",account);
                        break;
                    case R.id.menu2:
                        setCurrentFragment(searchFragment);
                        break;
                    case R.id.menu3:
                        setCurrentFragment(infoFragment);
                        break;
                    case R.id.menu4:
                        setCurrentFragment(customerFragment);
                        break;
                }

                return true;
            }

        });
        bottomNavigationView.setSelectedItemId(R.id.menu1);
        getInfoUser();
    }

    public void getInfoUser(){
        UserSQL userSQL = new UserSQL(getBaseContext());
        List<User> list = userSQL.getAll(account.getId());

        if(list.get(0).getName().equals("atc")) {
            uriImage = getURLForResource(R.drawable.bg);
        } else {
            uriImage = list.get(0).getImage();
        }
//        uriImage = userSQL.searchUserFollow(account.getId());
    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                .commit();
    }

}