package com.example.foodrecipes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodrecipes.R;
import com.example.foodrecipes.model.CompanyInfo;

import java.util.List;

public class CompaniesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CompanyInfo> mCompanyInfos;
    private OnCompanyListener mOnCompanyListener;

    public CompaniesRecyclerAdapter(OnCompanyListener mOnCompanyListener) {
        this.mOnCompanyListener = mOnCompanyListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_company_list_item, parent, false);
        return new CompanyViewHolder(view, mOnCompanyListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(mCompanyInfos.get(position).getUrlOfSymbol()).
                into(((CompanyViewHolder)holder).logo);

        ((CompanyViewHolder)holder).name.setText(mCompanyInfos.get(position).getName());
        ((CompanyViewHolder)holder).symbol.setText(mCompanyInfos.get(position).getSymbol());

    }

    @Override
    public int getItemCount() {
        if (mCompanyInfos != null) {
            return mCompanyInfos.size();
        }
        return 0;
    }

    public void setCompanies(List<CompanyInfo> companyInfos){
        mCompanyInfos = companyInfos;
        notifyDataSetChanged();
    }
}
