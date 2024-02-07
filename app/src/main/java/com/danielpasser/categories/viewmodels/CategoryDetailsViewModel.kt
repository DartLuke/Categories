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
    repository: ProductRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    /**
    Google doesn't recommend to pass complicated data between navigation components in compose. Instead we should pass key and take data from repository,
    database, network, etc. Official explanation is "Single source of truth" rule. A little bit strange. It wasn't a problem to pass parcable objects
    between fragments. Other option is to pass JSON.
     */
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
