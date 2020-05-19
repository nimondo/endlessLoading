package com.example.loadmore.utils.binding

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object BindingAdapters {
//    @BindingAdapter("countryValue")
//    @JvmStatic
//    fun setCountryValue(picker: CountryCodePicker, code: ObservableField<String>) {
//        code.set(picker.selectedCountryCodeWithPlus.toString())
//    }
@BindingAdapter("adapterLine")
@JvmStatic
fun setAdapterLine(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.itemAnimator = DefaultItemAnimator()
    adapter.setHasStableIds(true)
    recyclerView.adapter = adapter
}
}