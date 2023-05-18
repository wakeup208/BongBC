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

import com.example.myapplication.MainActivity;
import com.example.myapplication.db.UserSQL;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.Tour;

import java.util.ArrayList;
import java.util.List;
import com.example.myapplication.R;
import com.example.myapplication.ui.EditActivity;
import com.example.myapplication.ui.EditTourActivity;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ItemViewHolder> {
    Context context;
    List<Tour> listTour;
    OnItemListener listener;
    Boolean checkSearch;
    public TourAdapter(Context context, List<Tour> listTour, Boolean check) {
        this.context = context;
        this.listTour = listTour;
        this.checkSearch = check;
    }

    public TourAdapter(Context context){
        listTour = new ArrayList<>();
        this.context = context;
    }
    public void setClickListener(OnItemListener listener){
        this.listener = listener;
    }
    public void filterList(List<Tour> filter){
        listTour = filter;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tour_item, parent, false);
//        Account account = getIntent
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Tour tour = listTour.get(position);
        holder.txtName.setText("Địa điểm: " + tour.getName());
        holder.txtDuration.setText("Thời gian: " + tour.getDuration());
        holder.img.setImageURI(Uri.parse(tour.getImg()));
        String accountName = MainActivity.account.getUsername();
        if(accountName.equalsIgnoreCase("anhtuancao") == false) {
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTourActivity.class);
                intent.putExtra("tour", tour);
                context.startActivity(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSQL userSQL = new UserSQL(context);
                userSQL.deleteTour(tour);
                listTour.remove(tour);
                notifyDataSetChanged();
                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTour.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtName, txtDuration;
        ImageView img;
        ImageButton btnEdit, btnDelete;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.name);
            txtDuration = itemView.findViewById(R.id.duration);
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
    public Tour getItem(int position){
        return listTour.get(position);
    }
    public interface OnItemListener{
        public void onClick(View view, int position);
    }
}
