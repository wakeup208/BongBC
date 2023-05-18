package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.adapter.AccountAdapter;
import com.example.myapplication.db.UserSQL;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.Tour;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.model.User;
import com.example.myapplication.R;
public class CustomerFragment extends Fragment implements AccountAdapter.OnItemListener {
    RecyclerView rcv;
    List<Account> listAccount = new ArrayList<>();
    AccountAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        rcv = view.findViewById(R.id.rcv_list_item);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        UserSQL sqLiteHelper = new UserSQL(getContext());
        listAccount = sqLiteHelper.getAllAccount();
        adapter = new AccountAdapter(getContext(), listAccount, false);
        adapter.setClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

    }

    @Override
    public void onClick(View view, int position) {
        Account account = adapter.getItem(position);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("account", account);
        getContext().startActivity(intent);
    }
}
