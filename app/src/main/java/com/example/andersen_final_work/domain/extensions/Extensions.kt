package com.example.andersen_final_work.domain.extensions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Spinner.onItemSelect(callback: (item: String) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parentView: AdapterView<*>?,
            selectedItemView: View?,
            position: Int,
            id: Long
        ) {
            callback(parentView?.getItemAtPosition(position).toString())
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
        }
    }
}

fun RecyclerView.getCurrentPosition(): Int {
    return (this.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
}