package com.example.androidcashier.ui.invoice;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcashier.OnClicCuantaty;
import com.example.androidcashier.R;
import com.example.androidcashier.adabters.AdabterInvoice;


import java.util.ArrayList;

public class RetuenAdabter extends RecyclerView.Adapter<RetuenAdabter.viewholderRetuen>{
    ArrayList<ReturnModel> list=new ArrayList<>();
    Context context;
    OnClicCuantaty clic;

    public RetuenAdabter(Context context, OnClicCuantaty clic) {
        this.context = context;
        this.clic = clic;
    }

    public RetuenAdabter(Context context) {
        this.context = context;
    }

    public ArrayList<ReturnModel> getList() {
        return list;
    }

    public void setList(ArrayList<ReturnModel> list) {
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public RetuenAdabter.viewholderRetuen onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_row, parent, false);
        viewholderRetuen viewholder=new viewholderRetuen(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RetuenAdabter.viewholderRetuen holder, int position)
    {

        holder.  name_v.setText(list.get(position).getItemName());
        //  holder.   total_v.setText(list.get(position).getBarcode());
        holder.  price_v.setText(list.get(position).getSelling_Price()+"");
        Double xx=list.get(position).getSelling_Price()*list.get(position).getContaty();
        list.get(position).setBalanc(xx);
        holder.  total_v.setText(xx+"");

        holder.  contaty_v.setText(list.get(position).getContaty()+"");
        holder.  contaty_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer>arrayList=new ArrayList<>();

                // set value in array list
                arrayList.add(1);
                arrayList.add(2);
                arrayList.add(3);
                arrayList.add(4);
                arrayList.add(5);
                arrayList.add(6);
                arrayList.add(7);
                arrayList.add(8);
                arrayList.add(9);
                arrayList.add(10);
                arrayList.add(11);
                arrayList.add(12);
                arrayList.add(13);
                arrayList.add(14);
                arrayList.add(15);

                // Initialize dialog
                Dialog dialog=new Dialog(context);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(600,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                // EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);

                // Initialize array adapter
                ArrayAdapter<Integer> adapterlist=new ArrayAdapter<Integer>(context, android.R.layout.simple_list_item_1,arrayList);

                // set adapter
                listView.setAdapter(adapterlist);

                // editText.addTextChangedListener(new TextWatcher() {
                //     @Override
                //     public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //         //  Toast.makeText(ItemsActivity.this, s+"s34", Toast.LENGTH_SHORT).show();

                //     }

                //     @Override
                //     public void onTextChanged(CharSequence s, int start, int before, int count) {
                //         adapterlist.getFilter().filter(s);
                //         //  adapterlist.filterList()

                //         Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                //     }

                //     @Override
                //     public void afterTextChanged(Editable s) {
                //         //  Toast.makeText(ItemsActivity.this, s+"ss", Toast.LENGTH_SHORT).show();

                //     }
                // });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        //   textview.setText(adapterlist.getItem(position).getItemName());
                        //   // Toast.makeText(ItemsActivity.this,adapter.getItem(position).Record_ID, Toast.LENGTH_SHORT).show();
                        //   list.add(new ItemsModel(adapterlist.getItem(position).getSelling_Price(),adapterlist.getItem(position).getItemName(),adapterlist.getItem(position).getBarcode()));
                        //   // Dismiss dialog
                        //   //  adabter.setList(list);
                        //   adabter.notifyItemInserted(list.size()-1);
                        //   billRecycler.scrollToPosition(list.size());
                        //   billRecycler.setAdapter(adabter);
                        holder.   contaty_v.setText(adapterlist.getItem(position)+"");
                        dialog.dismiss();
                    }
                });
            }
        });



        holder.  contaty_v.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



                //  Double.valueOf(String.valueOf(charSequence));
                //  Toast.makeText(context, i+"", Toast.LENGTH_SHORT).show();
                Double sum=0.0;

                try {
                    sum=  Double.valueOf(String.valueOf(charSequence))*list.get(position).getSelling_Price();
                    // holder.

                    list.get(position).setBalanc(sum);
                    holder.total_v.setText(list.get(position).getBalanc()+"");
                    list.get(position).setContaty(Double.valueOf(String.valueOf(charSequence)));
                    clic.cliceCuantaty(position,list.get(position).getBalanc());
                }catch (Exception e){
                    Log.d("teeest",e.getMessage());
                }


                //  if (Double.valueOf(String.valueOf(charSequence))==null) {
                //
                //         // Double.valueOf(String.valueOf(charSequence))=0.0;
                //      list.get(position).setBalanc(0.0);

                //      }else {
                //      //sum= Double.valueOf((String) charSequence)*list.get(position).getSelling_Price();
                //      sum=  Double.valueOf(String.valueOf(charSequence))*list.get(position).getSelling_Price();
                //      // holder.
                //      list.get(position).setBalanc(sum);
                //      holder.  total_v.setText(list.get(position).getBalanc()+"");
                //      list.get(position).setContaty(Double.valueOf(String.valueOf(charSequence)));
                //  }

                //  holder.  total_v.setText(sum+"");
                //   holder.   total_v.setText()
                // Toast.makeText(context, sum+"", Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, i1, Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, i2, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //  Toast.makeText(context, editable, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholderRetuen extends RecyclerView.ViewHolder {
        TextView name_v,total_v,price_v;
        TextView contaty_v;
        public viewholderRetuen(@NonNull View itemView) {
            super(itemView);
            name_v   =itemView.findViewById(R.id.name_v);
            total_v  =itemView.findViewById(R.id.total_v);
            price_v   =itemView.findViewById(R.id.price_v);
            contaty_v =itemView.findViewById(R.id.contaty_v);
        }
    }
}
