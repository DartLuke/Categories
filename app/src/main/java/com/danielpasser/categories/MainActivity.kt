package com.danielpasser.categories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.danielpasser.categories.compose.CategoriesApp
import com.danielpasser.categories.ui.theme.CategoriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CategoriesTheme {
                CategoriesApp()
            }
        }
    }
}
