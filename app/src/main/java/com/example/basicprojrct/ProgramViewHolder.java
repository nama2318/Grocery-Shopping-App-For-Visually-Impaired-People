package com.example.basicprojrct;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder {

    ImageView itemImage;
    TextView prodName;
    TextView prodPrice;

    ProgramViewHolder(View v){
        itemImage = v.findViewById(R.id.proimg);
        prodName = v.findViewById(R.id.prodname);
        prodPrice = v.findViewById(R.id.prodprice);
    }
}
