package com.example.foodrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.foodrecipes.model.CompanyInfo;

public class CompanyInfoActivity extends AppCompatActivity {

    //UI components
    private ScrollView mScrollView;
    private AppCompatImageView mLogoImageView;
    private TextView mCompanyNameTextView, mCompanySymbolTextView,
    mCompanyAnalystTargetPrice, mCompanyCountryTextView,
    mCompanySectorTextView, mCompanyDividendPerShareTextView,
    mCompanyDividendDate, mCompanyAssetType, mCompanyDesription;

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
    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("companyInfo")){
            CompanyInfo companyInfo = getIntent().getParcelableExtra("companyInfo");
        }
    }
}
