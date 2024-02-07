package com.danielpasser.categories.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielpasser.categories.models.Product
import com.danielpasser.categories.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {
    val categories: StateFlow<Map<String?, List<Product>>> =
        repository.getProductsDao().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = hashMapOf()
        )

    init {
        geProducts()
    }

    private fun geProducts() {
        viewModelScope.launch {
            try {
                repository.getProductsFromServer()
            } catch (e: Exception) {
                Log.e("TEST", "Exception: ${e}, ${e.cause}")
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
