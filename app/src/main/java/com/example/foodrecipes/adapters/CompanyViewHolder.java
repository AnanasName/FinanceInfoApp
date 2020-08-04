package com.example.foodrecipes.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipes.R;

public class CompanyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView symbol, name;
    ImageView logo;
    OnCompanyListener onCompanyListener;

    public CompanyViewHolder(@NonNull View itemView, OnCompanyListener onCompanyListener) {
        super(itemView);

        this.onCompanyListener = onCompanyListener;

        name = itemView.findViewById(R.id.item_company_name_text_view);
        symbol = itemView.findViewById(R.id.item_company_symbol_text_view);
        logo = itemView.findViewById(R.id.item_company_logo_image_view);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onCompanyListener.onCompanyClick(getAdapterPosition());
    }
}
