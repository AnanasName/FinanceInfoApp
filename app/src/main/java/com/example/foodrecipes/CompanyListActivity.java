package com.example.foodrecipes;

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
import com.example.foodrecipes.viewmodels.CompanyListViewModel;

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
        mAdapter = new CompaniesRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void searchCompaniesApi(String symbols){
        mCompanyListViewModel.searchCompaniesApi(symbols);
    }

    @Override
    public void onCompanyClick(int position) {

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
}