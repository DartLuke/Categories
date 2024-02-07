package com.danielpasser.categories.compose.categorieslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.danielpasser.categories.R
import com.danielpasser.categories.models.Product
import com.danielpasser.categories.viewmodels.ProductListViewModel

@Composable
fun CategoriesListScreen(
    modifier: Modifier = Modifier,
    onClickItem: (String?) -> Unit,
    viewModel: ProductListViewModel = hiltViewModel(),
) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar()
    }) { contentPadding ->
        CategoriesList(
            modifier = Modifier.padding(
                top = contentPadding.calculateTopPadding(),
                start = dimensionResource(id = R.dimen.padding_small),
                end = dimensionResource(id = R.dimen.padding_small),
                bottom = dimensionResource(id = R.dimen.padding_small)
            ),
            onClickItem = onClickItem,
            categories = viewModel.categories.collectAsState().value
        )
    }
}

@Composable
private fun CategoriesList(
    modifier: Modifier = Modifier,
    onClickItem: (String?) -> Unit,
    categories: Map<String?, List<Product>>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        items(items = categories.toList()) { product ->
            CategoryItem(product, onClickItem = onClickItem)
        }
    }
}

@Composable
private fun CategoryItem(
    categories: Pair<String?, List<Product>>,
    onClickItem: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = { onClickItem(categories.first) }),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Thumbnail(categories.second)
            Text(
                text = categories.first ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.distinct_products, categories.second.size),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(
                    R.string.in_stock,
                    categories.second.sumOf { it.stock ?: 0 }),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Thumbnail(products: List<Product>, modifier: Modifier = Modifier) {
    if (products.isNotEmpty()) (GlideImage(
        modifier = modifier,
        model = products.first().thumbnail,
        contentDescription = ""
    ))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }, modifier = modifier
    )
}
