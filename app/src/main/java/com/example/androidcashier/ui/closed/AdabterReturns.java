package com.example.androidcashier.ui.closed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcashier.R;
import com.example.androidcashier.adabters.AdabterInvoice;
import com.example.androidcashier.pojo.ItemsModel;
import com.example.androidcashier.pojo.close.CloseModel;

import java.util.ArrayList;

public class AdabterReturns  extends RecyclerView.Adapter<AdabterReturns.viewholderReturns>{
    ArrayList<CloseModel> list=new ArrayList<>();

    Context context;

    public void setList(ArrayList<CloseModel> list) {
        this.list = list;
    }

    public AdabterReturns(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AdabterReturns.viewholderReturns onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_row, parent, false);
        viewholderReturns viewholder = new viewholderReturns(view);
        //  Toast.makeText(context, viewType, Toast.LENGTH_SHORT).show();

        return viewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull AdabterReturns.viewholderReturns holder, int position) {
        holder.name_v.setText(list.get(position).getItems());
        holder.total_v.setText(list.get(position).getBillDate().replace("T00:00:00",""));
        holder.contaty_v.setText(list.get(position).getTotalQty()+"");
        holder.price_v.setText(list.get(position).getTotalPrice()+" ج.م ");
    //    holder.name_v.setText(list.get(position).getItems());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholderReturns extends RecyclerView.ViewHolder {
        TextView name_v,total_v,price_v;
        TextView contaty_v;
        public viewholderReturns(@NonNull View itemView) {
            super(itemView);
            name_v   =itemView.findViewById(R.id.name_v);
            total_v  =itemView.findViewById(R.id.total_v);
            price_v   =itemView.findViewById(R.id.price_v);
            contaty_v =itemView.findViewById(R.id.contaty_v);

        }
    }
}
