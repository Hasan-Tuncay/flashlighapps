package com.example.casestudy.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casestudy.api.ApiUtils
import com.example.casestudy.model.App
import com.example.casestudy.model.Category
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch


class HomeViewModel() : ViewModel() {
    private val _appList = MutableLiveData<List<App>>()
    val appList: LiveData<List<App>> = _appList
    fun setApplist(list: List<App>) {
        _appList.value = list
    }

    private var _selectedCategory = MutableLiveData<Category>()
    val selectedCategory: LiveData<Category> = _selectedCategory

    fun setSelectedCategory(category: Category) {
        _selectedCategory.value = category
    }

    fun getFlashlights(context: Context, onError: Runnable) {

        viewModelScope.launch {
            try {
                val response = ApiUtils.getApi(Category.FLASH_LIGHTS).getFlashLights()
                if (response.isSuccessful) {
                    _appList.value = response.body()
                } else {
                    onError.run()
                }
            } catch (e: Exception) {
                onError.run()
            }
        }
    }

    fun getColorligts(context: Context, onError: Runnable) {
        viewModelScope.launch {
            try {
                val response = ApiUtils.getApi(Category.COLORED_LIGHTS).getColorlights()
                if (response.isSuccessful) {
                    _appList.value = response.body()
                } else {
                    onError.run()
                }
            } catch (e: Exception) {
                onError.run()
            }
        }

    }

    fun getSosalerts(context: Context, onError: Runnable) {
        viewModelScope.launch {
            try {
                val response = ApiUtils.getApi(Category.SOS_ALERTS).getSosalerts()
                if (response.isSuccessful) {
                    _appList.value = response.body()
                } else {
                    onError.run()
                }
            } catch (e: Exception) {
                onError.run()
            }
        }
    }
}