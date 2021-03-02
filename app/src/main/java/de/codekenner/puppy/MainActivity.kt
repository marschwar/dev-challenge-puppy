package de.codekenner.puppy

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.livedata.observeAsState
import de.codekenner.puppy.ui.theme.PuppyTheme

class MainActivity : AppCompatActivity() {

    private val viewModel: PuppyViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PuppyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val observeAsState = viewModel.viewState.observeAsState()

                    observeAsState.value?.let { viewState ->
                        Crossfade(targetState = viewState, animationSpec = tween(1000)) { state ->
                            when (state) {
                                is PuppyList -> PuppyListScreen(
                                    state,
                                    onViewEvent = viewModel::onViewEvent
                                )
                                is PuppyDetails -> PuppyDetailsScreen(
                                    puppyDetails = state
                                )
                            }
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
