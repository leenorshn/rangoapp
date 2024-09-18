package com.avenir.rangoapp.data.datasource

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharePrefDB @Inject constructor(
   private val context: Context
) {
    private  var  share: SharedPreferences = context.getSharedPreferences(
        "com.avenir.rangoapp", Context.MODE_PRIVATE,
    )


    fun getCompany():String{
        val id= share.getString("company","")
        return "$id"
    }
   // @SuppressLint("CommitPrefEdits")
    fun setCompany(id:String){
        share.edit().putString("company",id)
    }

}