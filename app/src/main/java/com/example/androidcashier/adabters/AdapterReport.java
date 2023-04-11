package com.example.androidcashier.adabters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcashier.R;
import com.example.androidcashier.pojo.Report;

import java.util.ArrayList;

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.viewholderReports>{
        ArrayList<Report> list=new ArrayList<>();

        Context context;

    public AdapterReport(Context context) {
        this.context = context;
    }

    public ArrayList<Report> getList() {
        return list;
    }

    public void setList(ArrayList<Report> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public viewholderReports onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_row, parent, false);
        viewholderReports viewholder = new viewholderReports(view);
        //  Toast.makeText(context, viewType, Toast.LENGTH_SHORT).show();
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderReports holder, int position) {

        holder.  name_v.setText(list.get(position).items+"");
        //  holder.   total_v.setText(list.get(position).getBarcode());
        holder.  price_v.setText(list.get(position).TotalPrice+"");

        holder.  contaty_v.setText(list.get(position).totalQty+"");

        holder. total_v  .setText(list.get(position).BillDate.replace("T00:00:00","")+"");
    }

    @Override
public int getItemCount() {
        return list.size();
        }

public class viewholderReports extends RecyclerView.ViewHolder {
    TextView name_v,total_v,price_v;
    TextView contaty_v;
    public viewholderReports(@NonNull View itemView) {
        super(itemView);
        name_v   =itemView.findViewById(R.id.name_v);
        total_v  =itemView.findViewById(R.id.total_v);
        price_v   =itemView.findViewById(R.id.price_v);
        contaty_v =itemView.findViewById(R.id.contaty_v);

    }
}
}
