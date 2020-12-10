package com.example.foodrecipes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodrecipes.databinding.ActivityCompanyInfoBinding
import com.example.foodrecipes.model.CompanyInfo
import com.example.foodrecipes.util.Resource
import com.example.foodrecipes.viewmodels.CompanyInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class CompanyInfoActivity : AppCompatActivity() {

    private val viewModel: CompanyInfoViewModel by viewModels()
    private lateinit var companyInfo: CompanyInfo

    private lateinit var binding: ActivityCompanyInfoBinding

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        subscribeObservers()
        getIncomingIntent()
        viewModel.getCompanyInfo(companyInfo.symbol)
    }

    private fun getIncomingIntent(){
        if (intent.hasExtra("companyInfo")){
            companyInfo = intent.getParcelableExtra("companyInfo")
        }
    }

    private fun subscribeObservers() {
        viewModel.company.observe(this, Observer { companyInfo ->
            if (companyInfo != null) {
                when(companyInfo.status){
                    Resource.Status.SUCCESS -> {
                        //hideProgressBar()
                        companyInfo.data?.let { setProperties(it) }
                    }
                    Resource.Status.LOADING -> {
                        //showProgressBar()
                    }
                    Resource.Status.ERROR -> {
                        //hideProgressBar()
                        displayErrorScreen()
                    }

                }
            }
        })
    }

    private fun setProperties(companyInfo: CompanyInfo) {
        binding.apply {
            companyInfoContainer.visibility = View.VISIBLE
            Glide.with(this@CompanyInfoActivity)
                    .load(companyInfo.urlOfSymbol)
                    .placeholder(R.drawable.ic_baseline_maximize_24)
                    .error(android.R.drawable.stat_notify_error)
                    .into(companyInfoLogoImageView)
            if (companyInfo.description != null) {
                companyInfoDescriptionTextView.text = companyInfo.getDescription()
            } else {
                companyInfoDescriptionTextView.text = StringBuilder().append("Description: ").append("No information")
            }
            companyInfoNameTextView.text = companyInfo.name
            companyInfoSymbolTextView.text = companyInfo.symbol
            if (companyInfo.analystTargetPrice != null) {
                companyInfoAnalystTargetPriceTextView.text = StringBuilder().append("Analyst target price: ").append(companyInfo.getAnalystTargetPrice())
            } else {
                companyInfoAnalystTargetPriceTextView.text = StringBuilder().append("Analyst target price: ").append("No information")
            }
            if (companyInfo.assetType != null) {
                companyInfoAssetTypeTextView.text = StringBuilder().append("Asset type: ").append(companyInfo.getAssetType())
            } else {
                companyInfoAssetTypeTextView.text = StringBuilder().append("Asset type: ").append("No information")
            }
            if (companyInfo.country != null) {
                companyInfoCountryTextView.text = StringBuilder().append("Country: ").append(companyInfo.getCountry())
            } else {
                companyInfoCountryTextView.text = StringBuilder().append("Country: ").append("No information")
            }
            if (companyInfo.sector != null) {
                companyInfoSectorTextView.text = StringBuilder().append("Sector: ").append(companyInfo.getSector())
            } else {
                companyInfoSectorTextView.text = StringBuilder().append("Sector: ").append("No information")
            }
            if (companyInfo.dividendDate != null) {
                companyInfoDividendDateTextView.text = StringBuilder().append("Dividend date: ").append(companyInfo.getDividendDate())
            } else {
                companyInfoDividendDateTextView.text = StringBuilder().append("Dividend date: ").append("No information")
            }
            if (companyInfo.dividendPerShare != null) {
                companyInfoDividendPerShareTextView.text = StringBuilder().append("Dividend per share: ").append(companyInfo.getDividendPerShare())
            } else {
                companyInfoDividendPerShareTextView.text = StringBuilder().append("Dividend per share: ").append("No information")
            }
            if (companyInfo.price > 0.0) {
                companyInfoPriceTextView.text = StringBuilder().append("Price: ").append(companyInfo.getPrice())
            } else {
                companyInfoPriceTextView.text = "No information"
            }
        }
    }

    private fun displayErrorScreen() {
        binding.companyInfoContainer.visibility = View.GONE
        binding.companyInfoContainerForError.visibility = View.VISIBLE
    }

//    private fun hideProgressBar(){
//        binding.mainCompaniesList.visibility = View.VISIBLE
//        binding.mainDottedProgressBar.root.visibility = View.GONE
//    }
//
//    private fun showProgressBar(){
//        binding.mainCompaniesList.visibility = View.GONE
//        binding.mainDottedProgressBar.root.visibility = View.VISIBLE
//    }
}
