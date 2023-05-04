package com.example.myapplication

import android.util.SparseBooleanArray
import android.view.View

interface OnBrandClickListener {

    fun onBrandClick(
        holder: BrandAdapter.ViewHolder?,
        view: View?,
        position: Int,
        checkStatus: SparseBooleanArray,
        text1: CharSequence,
        text2: CharSequence,
        text3: CharSequence,
        text4: CharSequence,
        text5:CharSequence,
        text6: CharSequence
    )

}