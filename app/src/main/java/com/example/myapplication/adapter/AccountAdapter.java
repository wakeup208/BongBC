package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.UserSQL;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.EditActivity;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ItemViewHolder> {
    Context context;
    List<Account> listAccount;
    OnItemListener listener;
    Boolean checkSearch;
    public AccountAdapter(Context context, List<Account> listAccount, Boolean check) {
        this.context = context;
        this.listAccount = listAccount;
        this.checkSearch = check;
    }

    public AccountAdapter(Context context){
        listAccount = new ArrayList<>();
        this.context = context;
    }
    public void setClickListener(OnItemListener listener){
        this.listener = listener;
    }
    public void filterList(List<Account> filter){
        listAccount = filter;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.account_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Account account = listAccount.get(position);
        holder.txtName.setText("User: " + account.getUsername());
//        holder.txtGender.setText("Giới tính: " + account.getId());
//        UserSQL userSQL = new UserSQL();
//        userSQL.get
        if(checkSearch) {
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSQL userSQL = new UserSQL(context);
                userSQL.deleteAccount(account);
                listAccount.remove(account);
                notifyDataSetChanged();
                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAccount.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtName, txtBirthday, txtGender,txtRole;
        ImageView img;
        ImageButton btnEdit, btnDelete;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.name);
            txtBirthday = itemView.findViewById(R.id.duration);
//            txtGender = itemView.findViewById(R.id.gender);
            img = itemView.findViewById(R.id.im);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
    public Account getItem(int position){
        return listAccount.get(position);
    }
    public interface OnItemListener{
        public void onClick(View view, int position);
    }

}
