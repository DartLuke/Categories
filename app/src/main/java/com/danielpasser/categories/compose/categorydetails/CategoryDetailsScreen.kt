package com.danielpasser.categories.compose.categorydetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.danielpasser.categories.viewmodels.CategoryDetailsViewModel

@Composable
fun CategoryDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryDetailsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CategoryDetailsTopAppBar(title = viewModel.category, onBackPressed = onBackPressed)
        }
    ) { contentPadding ->
        CategoryDetails(
            Modifier.padding(
                top = contentPadding.calculateTopPadding(),
                start = dimensionResource(id = R.dimen.padding_small),
                end = dimensionResource(id = R.dimen.padding_small),
                bottom = dimensionResource(id = R.dimen.padding_small)
            ), viewModel = viewModel
        )
    }
}

@Composable
fun CategoryDetails(modifier: Modifier = Modifier, viewModel: CategoryDetailsViewModel) {
    ProductList(products = viewModel.productByCategory.collectAsState().value, modifier = modifier)
}

@Composable
private fun ProductList(
    modifier: Modifier = Modifier,
    products: List<Product>
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        items(items = products)
        { product ->
            ProductItem(product = product)
        }
    }
}

@Composable
private fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProductItemImage(product.images)
            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(
                    text = product.title ?: "",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.price, product.price ?: -1),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(
                        R.string.in_stock,
                        product.stock ?: 0
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProductItemImage(images: List<String>, modifier: Modifier = Modifier) {
    if (images.isNotEmpty()) (GlideImage(
        modifier = modifier.size(95.dp),
        model = images.first(),
        contentDescription = "",
        contentScale = ContentScale.FillBounds,

        ))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryDetailsTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displaySmall
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    )
}