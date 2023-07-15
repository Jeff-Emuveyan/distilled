package com.example.distilled.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import com.example.distilled.ui.theme.DistilledTheme
import com.example.movies.ui.MoviesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DistilledTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.semantics { testTagsAsResourceId = true }.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MoviesScreen()
                }
            }
        }
    }
}