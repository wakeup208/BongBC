package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.adapter.TourAdapter;
import com.example.myapplication.db.UserSQL;
import com.example.myapplication.model.Tour;
import com.example.myapplication.model.User;

import java.util.List;

public class SearchFragment extends Fragment implements ItemAdapter.OnItemListener, TourAdapter.OnItemListener {
    private final String[] listPosition = {
            "Khách hàng", "Tour"
    };
    RecyclerView rcv;
    Button  btnSearch;
    EditText edtSearch;
    RadioButton btnCus,btnTour;
    Spinner spType;
    TextView txtStatistic;
    List<User> listUser;
    List<Tour> listTour;
    ItemAdapter adapter;
    TourAdapter tourAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        rcv = view.findViewById(R.id.rcv);
        btnSearch = view.findViewById(R.id.btn_search);
        edtSearch = view.findViewById(R.id.edt_search);
        btnCus = view.findViewById(R.id.radio_customer);
        btnTour = view.findViewById(R.id.radio_tour);
        txtStatistic = view.findViewById(R.id.txt_statistic);
        adapter = new ItemAdapter(getContext());
        tourAdapter = new TourAdapter(getContext());
        adapter.setClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        return view;
    }


    private void search() {
        UserSQL sqLiteHelper = new UserSQL(getContext());
        String searchText = edtSearch.getText().toString();
        if(searchText.isEmpty()){
            Toast.makeText(getContext(), "Vui lòng nhập thông tin tìm kiếm!", Toast.LENGTH_SHORT).show();
        }
        else{
            if(btnCus.isChecked()){
                listUser = sqLiteHelper.search(searchText);
                adapter = new ItemAdapter(getContext(), listUser, true);
                adapter.setClickListener(this);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                rcv.setLayoutManager(manager);
                rcv.setAdapter(adapter);
            }

            else if(btnTour.isChecked()){
                listTour = sqLiteHelper.searchTour(searchText);
                tourAdapter = new TourAdapter(getContext(), listTour, true);
                tourAdapter.setClickListener(this);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                rcv.setLayoutManager(manager);
                rcv.setAdapter(tourAdapter);
            }
        }

    }

    @Override
    public void onClick(View view, int position) {

        if (btnCus.isChecked()){
            User user = adapter.getItem(position);
            Intent intent = new Intent(getContext(), DetailActivity.class);
            Log.d("TAGLOG", view.toString());
            intent.putExtra("user", user);
            getContext().startActivity(intent);
        }
        else if (btnTour.isChecked()){
            Tour tour = tourAdapter.getItem(position);
            Intent intent = new Intent(getContext(), DetailTourActivity.class);
            Log.d("TAGLOG", view.toString());
            intent.putExtra("tour", tour);
            getContext().startActivity(intent);
        }

    }
}
