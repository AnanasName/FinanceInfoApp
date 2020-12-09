package com.example.foodrecipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.adapters.CompaniesRecyclerAdapter
import com.example.foodrecipes.adapters.OnCompanyListener
import com.example.foodrecipes.databinding.ActivityCompaniesListBinding
import com.example.foodrecipes.model.CompanyInfo
import com.example.foodrecipes.util.Resource
import com.example.foodrecipes.util.VerticalSpacingItemDecorator
import com.example.foodrecipes.viewmodels.CompanyListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_companies_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import javax.inject.Inject

const val DEBUG = "DEBUG1"

@AndroidEntryPoint
class CompanyListActivity : BaseActivity(), OnCompanyListener {

    private val viewModel: CompanyListViewModel by viewModels()
    private lateinit var binding: ActivityCompaniesListBinding

    @Inject
    lateinit var itemDecorator: VerticalSpacingItemDecorator

    private var mAdapter: CompaniesRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompaniesListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRecyclerView()
        subscribeObservers()
        initSearchView()
        setStartData()
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    private fun subscribeObservers() {
        viewModel.companies.observe(this, Observer { companyInfos ->
            if (companyInfos != null) {
                when(companyInfos.status){
                    Resource.Status.SUCCESS -> {
                        hideProgressBar()
                        mAdapter?.setCompanies(companyInfos.data)
                    }
                    Resource.Status.LOADING -> {
                        showProgressBar()
                    }
                    Resource.Status.ERROR -> {
                        hideProgressBar()
                        Toast.makeText(this, companyInfos.message, Toast.LENGTH_LONG).show()
                    }

                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.mainCompaniesList.addItemDecoration(itemDecorator)
        mAdapter = CompaniesRecyclerAdapter(this)
        binding.mainCompaniesList.adapter = mAdapter
        binding.mainCompaniesList.layoutManager = LinearLayoutManager(this)
    }

    @ExperimentalCoroutinesApi
    private fun searchCompaniesApi(symbols: String) {
        Log.d(DEBUG, "searchCompanies")
        viewModel.searchCompanies(symbols)
    }

    override fun onCompanyClick(position: Int) {
//        val intent = Intent(this, CompanyInfoActivity::class.java)
//        intent.putExtra("companyInfo", mAdapter!!.getSelectedCompany(position))
//        startActivity(intent)
    }

    @ExperimentalCoroutinesApi
    private fun initSearchView() {

        binding.mainSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchCompaniesApi(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun hideProgressBar(){
        binding.mainCompaniesList.visibility = View.VISIBLE
        binding.mainDottedProgressBar.root.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.mainCompaniesList.visibility = View.GONE
        binding.mainDottedProgressBar.root.visibility = View.VISIBLE
    }

    private fun setStartData() {
        val companyInfos: MutableList<CompanyInfo> = ArrayList()
        val companyInfo1 = CompanyInfo()
        companyInfo1.name = "Apple"
        companyInfo1.symbol = "AAPL"
        companyInfo1.urlOfSymbol = "https://storage.googleapis.com/iexcloud-hl37opg/api/logos/AAPL.png"
        companyInfos.add(companyInfo1)
        val companyInfo2 = CompanyInfo()
        companyInfo2.name = "Microsoft"
        companyInfo2.symbol = "MSFT"
        companyInfo2.urlOfSymbol = "https://storage.googleapis.com/iexcloud-hl37opg/api/logos/MSFT.png"
        companyInfos.add(companyInfo2)
        val companyInfo3 = CompanyInfo()
        companyInfo3.name = "Coca-Cola"
        companyInfo3.symbol = "KO"
        companyInfo3.urlOfSymbol = "https://storage.googleapis.com/iexcloud-hl37opg/api/logos/KO.png"
        companyInfos.add(companyInfo3)
        val companyInfo4 = CompanyInfo()
        companyInfo4.name = "Tesla"
        companyInfo4.symbol = "TSLA"
        companyInfo4.urlOfSymbol = "https://storage.googleapis.com/iexcloud-hl37opg/api/logos/TSLA.png"
        companyInfos.add(companyInfo4)
        val companyInfo5 = CompanyInfo()
        companyInfo5.name = "Nike"
        companyInfo5.symbol = "NKE"
        companyInfo5.urlOfSymbol = "https://storage.googleapis.com/iexcloud-hl37opg/api/logos/NKE.png"
        companyInfos.add(companyInfo5)
        val companyInfo6 = CompanyInfo()
        companyInfo6.name = "Amazon.com Inc"
        companyInfo6.symbol = "AMZN"
        companyInfo6.urlOfSymbol = "https://storage.googleapis.com/iexcloud-hl37opg/api/logos/AMZN.png"
        companyInfos.add(companyInfo6)
        val companyInfo7 = CompanyInfo()
        companyInfo7.name = "Facebook Inc"
        companyInfo7.symbol = "FB"
        companyInfo7.urlOfSymbol = "https://storage.googleapis.com/iexcloud-hl37opg/api/logos/FB.png"
        companyInfos.add(companyInfo7)
        val companyInfo8 = CompanyInfo()
        companyInfo8.name = "Walt Disney Company"
        companyInfo8.symbol = "FB"
        companyInfo8.urlOfSymbol = "https://storage.googleapis.com/iexcloud-hl37opg/api/logos/DIS.png"
        companyInfos.add(companyInfo8)
        mAdapter!!.setCompanies(companyInfos)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_back_to_main_page) {
            setStartData()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.company_search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}