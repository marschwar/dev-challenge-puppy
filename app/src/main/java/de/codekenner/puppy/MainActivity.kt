package de.codekenner.puppy

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.codekenner.puppy.ui.theme.PuppyTheme

class MainActivity : AppCompatActivity() {

    private val viewModel: PuppyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PuppyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val observeAsState = viewModel.viewState.observeAsState()

                    observeAsState.value?.let { viewState ->
                        when (viewState) {
                            is PuppyList -> PuppyListScreen(
                                viewState,
                                onViewEvent = viewModel::onViewEvent
                            )
                            is PuppyDetails -> PuppyDetailsScreen(
                                puppyDetails = viewState
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (viewModel.onBackPressed()) {
            super.onBackPressed()
        }
    }
}
