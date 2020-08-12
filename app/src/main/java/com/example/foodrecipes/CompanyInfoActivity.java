package com.example.foodrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodrecipes.model.CompanyInfo;
import com.example.foodrecipes.viewmodels.CompanyInfoViewModel;

public class CompanyInfoActivity extends AppCompatActivity {

    private CompanyInfoViewModel mCompanyInfoViewModel;

    //UI components
    private ScrollView mScrollView;
    private ImageView mLogoImageView;
    private TextView mCompanyNameTextView, mCompanySymbolTextView,
            mCompanyAnalystTargetPrice, mCompanyCountryTextView,
            mCompanySectorTextView, mCompanyDividendPerShareTextView,
            mCompanyDividendDate, mCompanyAssetType, mCompanyDesription;
    private RelativeLayout mDottedProgressBar;
    private CompanyInfo mCompanyInfo;
    private RelativeLayout mContainer;
    private TextView mCompanyPriceTextView;
    private RelativeLayout mContainerForErrors;

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
        mCompanyPriceTextView = findViewById(R.id.company_info_price_text_view);
        mContainerForErrors = findViewById(R.id.company_info_container_for_error);

        mCompanyInfoViewModel = ViewModelProviders.of(this).get(CompanyInfoViewModel.class);
        subscribeObservers();
        getIncomingIntent();

        mCompanyInfoViewModel.getCompanyInfo(mCompanyInfo.getSymbol());
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("companyInfo")) {
            mCompanyInfo = getIntent().getParcelableExtra("companyInfo");
        }
    }

    private void subscribeObservers() {
        mCompanyInfoViewModel.getCompany().observe(this, new Observer<CompanyInfo>() {
            @Override
            public void onChanged(CompanyInfo companyInfo) {
                if (companyInfo != null) {
                    companyInfo.setUrlOfSymbol(mCompanyInfo.getUrlOfSymbol());
                    setProperties(companyInfo);
                }
            }
        });

        mCompanyInfoViewModel.isUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    mContainer.setVisibility(View.GONE);
                    mDottedProgressBar.setVisibility(View.VISIBLE);
                } else {
                    mDottedProgressBar.setVisibility(View.GONE);
                    //mContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        mCompanyInfoViewModel.hasRetrieveError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    displayErrorScreen();
                }
            }
        });
    }

    private void setProperties(CompanyInfo companyInfo) {
        mContainer.setVisibility(View.VISIBLE);

        Glide.with(this)
                .load(companyInfo.getUrlOfSymbol())
                .placeholder(R.drawable.ic_baseline_maximize_24)
                .error(android.R.drawable.stat_notify_error)
                .into(mLogoImageView);
        if (companyInfo.getDescription() != null) {
            mCompanyDesription.setText(companyInfo.getDescription());
        } else {
            mCompanyDesription.setText(new StringBuilder().append("Description: ").append("No information"));
        }

        mCompanyNameTextView.setText(mCompanyInfo.getName());
        mCompanySymbolTextView.setText(mCompanyInfo.getSymbol());

        if (companyInfo.getAnalystTargetPrice() != null) {
            mCompanyAnalystTargetPrice.setText(new StringBuilder().append("Analyst target price: ").append(companyInfo.getAnalystTargetPrice()));
        } else {
            mCompanyAnalystTargetPrice.setText(new StringBuilder().append("Analyst target price: ").append("No information"));
        }

        if (companyInfo.getAssetType() != null) {
            mCompanyAssetType.setText(new StringBuilder().append("Asset type: ").append(companyInfo.getAssetType()));
        } else {
            mCompanyAssetType.setText(new StringBuilder().append("Asset type: ").append("No information"));
        }

        if (companyInfo.getCountry() != null) {
            mCompanyCountryTextView.setText(new StringBuilder().append("Country: ").append(companyInfo.getCountry()));
        } else {
            mCompanyCountryTextView.setText(new StringBuilder().append("Country: ").append("No information"));
        }

        if (companyInfo.getSector() != null) {
            mCompanySectorTextView.setText(new StringBuilder().append("Sector: ").append(companyInfo.getSector()));
        } else {
            mCompanySectorTextView.setText(new StringBuilder().append("Sector: ").append("No information"));
        }

        if (companyInfo.getDividendDate() != null) {
            mCompanyDividendDate.setText(new StringBuilder().append("Dividend date: ").append(companyInfo.getDividendDate()));
        } else {
            mCompanyDividendDate.setText(new StringBuilder().append("Dividend date: ").append("No information"));
        }

        if (companyInfo.getDividendPerShare() != null) {
            mCompanyDividendPerShareTextView.setText(new StringBuilder().append("Dividend per share: ").append(companyInfo.getDividendPerShare()));
        } else {
            mCompanyDividendPerShareTextView.setText(new StringBuilder().append("Dividend per share: ").append("No information"));
        }

        if (companyInfo.getPrice() != 0.0) {
            mCompanyPriceTextView.setText(new StringBuilder().append("Price: ").append(companyInfo.getPrice()));
        } else {
            mCompanyPriceTextView.setText("No information");
        }

    }

    private void displayErrorScreen() {
        mContainer.setVisibility(View.GONE);
        mContainerForErrors.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        mCompanyInfoViewModel.setHasNotRetrieveError();
        super.onDestroy();
    }
}
