package com.example.foodrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodrecipes.adapters.CompanyViewHolder;
import com.example.foodrecipes.model.CompanyInfo;
import com.example.foodrecipes.viewmodels.CompanyInfoViewModel;

public class CompanyInfoActivity extends AppCompatActivity {

    private CompanyInfoViewModel mCompanyInfoViewModel;

    //UI components
    private ScrollView mScrollView;
    private AppCompatImageView mLogoImageView;
    private TextView mCompanyNameTextView, mCompanySymbolTextView,
    mCompanyAnalystTargetPrice, mCompanyCountryTextView,
    mCompanySectorTextView, mCompanyDividendPerShareTextView,
    mCompanyDividendDate, mCompanyAssetType, mCompanyDesription;
    private RelativeLayout mDottedProgressBar;
    private CompanyInfo mCompanyInfo;
    private RelativeLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);

        mScrollView = findViewById(R.id.parent);
        mLogoImageView = findViewById(R.id.company_info_logo_image_view);
        mCompanyNameTextView = findViewById(R.id.company_info_name_text_view);
        mCompanySymbolTextView = findViewById(R.id.company_info_symbol_text_view);
        mCompanyAnalystTargetPrice = findViewById(R.id.company_info_analyst_target_price_text_view);
        mCompanyCountryTextView = findViewById(R.id.company_info_country_text_view);
        mCompanySectorTextView = findViewById(R.id.company_info_sector_text_view);
        mCompanyDividendPerShareTextView = findViewById(R.id.company_info_dividend_per_share_text_view);
        mCompanyDividendDate = findViewById(R.id.company_info_dividend_date_text_view);
        mCompanyAssetType = findViewById(R.id.company_info_asset_type_text_view);
        mCompanyDesription = findViewById(R.id.company_info_description_text_view);
        mDottedProgressBar = findViewById(R.id.company_info_dotted_progress_bar);
        mContainer = findViewById(R.id.company_info_container);

        mCompanyInfoViewModel = ViewModelProviders.of(this).get(CompanyInfoViewModel.class);
        subscribeObservers();
        getIncomingIntent();

        mCompanyInfoViewModel.getCompanyInfo(mCompanyInfo.getSymbol());
    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("companyInfo")){
            mCompanyInfo = getIntent().getParcelableExtra("companyInfo");
        }
    }

    private void subscribeObservers(){
        mCompanyInfoViewModel.getCompany().observe(this, new Observer<CompanyInfo>() {
            @Override
            public void onChanged(CompanyInfo companyInfo) {
                if (companyInfo != null) {
                    setProperties(companyInfo);
                }
            }
        });

        mCompanyInfoViewModel.isUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    mContainer.setVisibility(View.GONE);
                    mDottedProgressBar.setVisibility(View.VISIBLE);
                }else{
                    mDottedProgressBar.setVisibility(View.GONE);
                    mContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setProperties(CompanyInfo companyInfo){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(mCompanyInfo.getUrlOfSymbol())
                .into(mLogoImageView);
        mCompanyDesription.setText(companyInfo.getDescription());
        mCompanyNameTextView.setText(mCompanyInfo.getName());
        mCompanySymbolTextView.setText(mCompanyInfo.getSymbol());
        mCompanyAnalystTargetPrice.setText(new StringBuilder().append("Ananlyst target price: ").append(companyInfo.getAnalystTargetPrice()).toString());
        mCompanyAssetType.setText(new StringBuilder().append("Asset type: ").append(companyInfo.getAssetType()).toString());
        mCompanyCountryTextView.setText(new StringBuilder().append("Country: ").append(companyInfo.getCountry()).toString());
        mCompanySectorTextView.setText(new StringBuilder().append("Sector: ").append(companyInfo.getSector()).toString());
        mCompanyDividendDate.setText(new StringBuilder().append("Dividend date: ").append(companyInfo.getDividendDate()).toString());
        mCompanyDividendPerShareTextView.setText(new StringBuilder().append("Dividend per share: ").append(companyInfo.getDividendPerShare()).toString());
    }
}
