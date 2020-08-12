package com.example.foodrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipes.adapters.CompaniesRecyclerAdapter;
import com.example.foodrecipes.adapters.OnCompanyListener;
import com.example.foodrecipes.model.CompanyInfo;
import com.example.foodrecipes.util.HorizontalDottedProgress;
import com.example.foodrecipes.util.Testing;
import com.example.foodrecipes.util.VerticalSpacingItemDecorator;
import com.example.foodrecipes.viewmodels.CompanyListViewModel;

import java.util.ArrayList;
import java.util.List;

public class CompanyListActivity extends BaseActivity implements OnCompanyListener {
    private CompanyListViewModel mCompanyListViewModel;
    private RecyclerView mRecyclerView;
    private CompaniesRecyclerAdapter mAdapter;
    private RelativeLayout mDottedProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_list);

        mCompanyListViewModel = ViewModelProviders.of(this).get(CompanyListViewModel.class);
        mRecyclerView = findViewById(R.id.main_companies_list);
        mDottedProgressBar = findViewById(R.id.main_dotted_progress_bar);


        initRecyclerView();
        subscribeObservers();
        initSearchView();
        setStartData();
    }

    private void subscribeObservers(){
        mCompanyListViewModel.getCompanies().observe(this, new Observer<List<CompanyInfo>>() {
            @Override
            public void onChanged(List<CompanyInfo> companyInfos) {
                if (companyInfos != null){
                    //Testing.printRecipes(companyInfos, "test companies");
                    mAdapter.setCompanies(companyInfos);
                }
            }
        });

        mCompanyListViewModel.isUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    Log.d("Update", aBoolean.toString());
                    mRecyclerView.setVisibility(View.GONE);
                    mDottedProgressBar.setVisibility(View.VISIBLE);
                }else{
                    Log.d("Update", aBoolean.toString());
                    mDottedProgressBar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initRecyclerView(){
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);
        mAdapter = new CompaniesRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void searchCompaniesApi(String symbols){
        mCompanyListViewModel.searchCompaniesApi(symbols);
    }

    @Override
    public void onCompanyClick(int position) {
        Intent intent = new Intent(this, CompanyInfoActivity.class);
        intent.putExtra("companyInfo", mAdapter.getSelectedCompany(position));
        startActivity(intent);
    }

    private void initSearchView(){
        final SearchView searchView = findViewById(R.id.main_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCompaniesApi(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void printData(View view) {
        Log.d("Test data", mCompanyListViewModel.getCompanies().getValue().get(1).getName());
    }

    private void setStartData() {
        List<CompanyInfo> companyInfos = new ArrayList<>();

        CompanyInfo companyInfo1 = new CompanyInfo();
        companyInfo1.setName("Apple");
        companyInfo1.setSymbol("AAPL");
        companyInfo1.setUrlOfSymbol("https://storage.googleapis.com/iexcloud-hl37opg/api/logos/AAPL.png");
        companyInfos.add(companyInfo1);

        CompanyInfo companyInfo2 = new CompanyInfo();
        companyInfo2.setName("Microsoft");
        companyInfo2.setSymbol("MSFT");
        companyInfo2.setUrlOfSymbol("https://storage.googleapis.com/iexcloud-hl37opg/api/logos/MSFT.png");
        companyInfos.add(companyInfo2);

        CompanyInfo companyInfo3 = new CompanyInfo();
        companyInfo3.setName("Coca-Cola");
        companyInfo3.setSymbol("KO");
        companyInfo3.setUrlOfSymbol("https://storage.googleapis.com/iexcloud-hl37opg/api/logos/KO.png");
        companyInfos.add(companyInfo3);

        CompanyInfo companyInfo4 = new CompanyInfo();
        companyInfo4.setName("Tesla");
        companyInfo4.setSymbol("TSLA");
        companyInfo4.setUrlOfSymbol("https://storage.googleapis.com/iexcloud-hl37opg/api/logos/TSLA.png");
        companyInfos.add(companyInfo4);

        CompanyInfo companyInfo5 = new CompanyInfo();
        companyInfo5.setName("Nike");
        companyInfo5.setSymbol("NKE");
        companyInfo5.setUrlOfSymbol("https://storage.googleapis.com/iexcloud-hl37opg/api/logos/NKE.png");
        companyInfos.add(companyInfo5);

        CompanyInfo companyInfo6 = new CompanyInfo();
        companyInfo6.setName("Amazon.com Inc");
        companyInfo6.setSymbol("AMZN");
        companyInfo6.setUrlOfSymbol("https://storage.googleapis.com/iexcloud-hl37opg/api/logos/AMZN.png");
        companyInfos.add(companyInfo6);

        CompanyInfo companyInfo7 = new CompanyInfo();
        companyInfo7.setName("Facebook Inc");
        companyInfo7.setSymbol("FB");
        companyInfo7.setUrlOfSymbol("https://storage.googleapis.com/iexcloud-hl37opg/api/logos/FB.png");
        companyInfos.add(companyInfo7);

        CompanyInfo companyInfo8 = new CompanyInfo();
        companyInfo8.setName("Walt Disney Company");
        companyInfo8.setSymbol("FB");
        companyInfo8.setUrlOfSymbol("https://storage.googleapis.com/iexcloud-hl37opg/api/logos/DIS.png");
        companyInfos.add(companyInfo8);

        mAdapter.setCompanies(companyInfos);
        mAdapter.notifyDataSetChanged();
    }
}