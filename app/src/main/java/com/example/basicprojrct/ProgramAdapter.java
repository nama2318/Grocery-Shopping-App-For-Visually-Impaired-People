package com.example.basicprojrct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ProgramAdapter extends ArrayAdapter<String> {
    Context context;
    int[] img;
    String[] name;
    String[] price;

    public ProgramAdapter( Context context, String[] name,String[] price,int[] img) {
        super(context, R.layout.listiitem,R.id.prodname,name);
        this.context = context;
        this.img =img;
        this.name=name;
        this.price=price;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        View singleItem = convertView;
        ProgramViewHolder holder = null;
        if(singleItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.listiitem,parent,false);
            holder = new ProgramViewHolder(singleItem);
            singleItem.setTag(holder);
        }
        else {
            holder = (ProgramViewHolder) singleItem.getTag();

        }
        holder.itemImage.setImageResource(img[position]);
        holder.prodName.setText(name[position]);
        holder.prodPrice.setText(price[position]);
        return singleItem;
    }
}
