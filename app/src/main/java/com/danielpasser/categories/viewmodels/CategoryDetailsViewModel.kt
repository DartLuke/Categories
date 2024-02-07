package com.danielpasser.categories.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielpasser.categories.models.Product
import com.danielpasser.categories.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoryDetailsViewModel @Inject constructor(
    private val repository: ProductRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    val category: String = savedStateHandle.get<String>(CATEGORY) ?: ""

    val productByCategory: StateFlow<List<Product>> =
        repository.getProductsByCategoryDao(category).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )

    companion object {
        const val CATEGORY = "CATEGORY"
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
