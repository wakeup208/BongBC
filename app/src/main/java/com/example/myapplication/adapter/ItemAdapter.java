package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.db.UserSQL;
import com.example.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;
import com.example.myapplication.R;
import com.example.myapplication.ui.EditActivity;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    Context context;
    List<User> listUser;
    OnItemListener listener;
    Boolean checkSearch;
    public ItemAdapter(Context context, List<User> listUser, Boolean check) {
        this.context = context;
        this.listUser = listUser;
        this.checkSearch = check;
    }

    public ItemAdapter(Context context){
        listUser = new ArrayList<>();
        this.context = context;
    }
    public void setClickListener(OnItemListener listener){
        this.listener = listener;
    }
    public void filterList(List<User> filter){
        listUser = filter;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        User user = listUser.get(position);
        holder.txtName.setText("Họ tên: " + user.getName());
        if (user.getName().equalsIgnoreCase("atc")){
            holder.txtPosition.setText("Chức vụ: Admin" );
        }
        else {
            holder.txtPosition.setText("Chức vụ: Khách hàng" );
        }
//        holder.img.setImageURI(Uri.parse(user.getImage())); // loi anh roi wtf v.cai uri nay` dung` nhu nao ong nhi.hay la thu comment xem co chay k :))
        if(checkSearch) {
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("user", user);
                context.startActivity(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSQL employeeSQL = new UserSQL(context);
                employeeSQL.deleteUser(user);
                listUser.remove(user);
                notifyDataSetChanged();
                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtName, txtPosition;
        ImageView img;
        ImageButton btnEdit, btnDelete;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.name);
            txtPosition = itemView.findViewById(R.id.position);
            img = itemView.findViewById(R.id.im);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
    public User getItem(int position){
        return listUser.get(position);
    }
    public interface OnItemListener{
        public void onClick(View view, int position);
    }
}
