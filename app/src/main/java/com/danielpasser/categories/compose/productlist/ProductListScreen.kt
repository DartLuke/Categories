package com.danielpasser.categories.compose.productlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.danielpasser.categories.R
import com.danielpasser.categories.models.Product
import com.danielpasser.categories.viewmodels.ProductListViewModel

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    onClickItem: (String?) -> Unit,
    viewModel: ProductListViewModel = hiltViewModel(),
) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar()
    }) { contentPadding ->
        ProductList(
            modifier = Modifier.padding(
                top = contentPadding.calculateTopPadding(),
                start = dimensionResource(id = R.dimen.padding_small),
                end = dimensionResource(id = R.dimen.padding_small),
                bottom = dimensionResource(id = R.dimen.padding_small)
            ),
            onClickItem = onClickItem,
            products = viewModel.productByCategory.collectAsState().value
        )
    }
}

@Composable
private fun ProductList(
    modifier: Modifier = Modifier,
    onClickItem: (String?) -> Unit,
    products: Map<String?, List<Product>>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        items(items = products.toList()) { product ->
            ProductItem(product, onClickItem = onClickItem)
        }
    }
}

@Composable
private fun ProductItem(
    product: Pair<String?, List<Product>>,
    onClickItem: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = { onClickItem(product.first) }),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProductItemThumbnail(product.second)
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(
                    text = product.first ?: "",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.distinct_products, product.second.size),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(
                        R.string.in_stock,
                        product.second.sumOf { it.stock ?: 0 }),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProductItemThumbnail(product: List<Product>, modifier: Modifier = Modifier) {
    if (product.isNotEmpty()) (GlideImage(
        modifier = modifier.size(95.dp),
        model = product.first().thumbnail,
        contentDescription = "",
        contentScale = ContentScale.FillBounds,

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
