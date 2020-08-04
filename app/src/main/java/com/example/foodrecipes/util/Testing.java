package com.example.foodrecipes.util;

import android.util.Log;

import com.example.foodrecipes.model.CompanyInfo;

import java.util.List;

public class Testing {

    public static void printRecipes(List<CompanyInfo> list, String tag){
        for (CompanyInfo info : list){
            Log.d(tag, "onChanged: " + info.getUrlOfSymbol() + " " + info.getName());
        }
    }
}
