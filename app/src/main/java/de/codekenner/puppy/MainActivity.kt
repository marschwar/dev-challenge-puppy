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
                                onFilterChanged = viewModel::onFilterChanged,
                                onItemSelected = viewModel::onItemSelected
                            )
                            is PuppyDetails -> PuppyDetailsScreen(puppyDetails = viewState)
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

@Composable
fun PuppyDetailsScreen(
    puppyDetails: PuppyDetails,
) {
    Text(text = puppyDetails.puppy.name, style = MaterialTheme.typography.h2)
}

@Composable
fun PuppyListScreen(
    puppyList: PuppyList,
    onFilterChanged: (String) -> Unit = {},
    onItemSelected: (Int) -> Unit = {},
) {
    Column {
        Text(text = "Welcome", style = MaterialTheme.typography.h2)
        SearchBar(puppyList.filter, onSearchTermChange = onFilterChanged)
        Puppies(puppyList.puppies, onItemSelected)
    }
}

@Composable
fun SearchBar(value: String, onSearchTermChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onSearchTermChange,
        label = { Text(text = "Search by name") },
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Puppies(puppies: List<Puppy>, onItemSelected: (Int) -> Unit) {
    LazyColumn {
        items(puppies, key = Puppy::id) { puppy ->
            Puppy(puppy, onItemSelected)
        }
    }
}

@Composable
fun Puppy(puppy: Puppy, onClick: (Int) -> Unit) {
    Row(modifier = Modifier
        .clickable { onClick(puppy.id) }
        .padding(16.dp)) {
        Column {
            Text(
                text = puppy.name,
                style = MaterialTheme.typography.h4,
                maxLines = 1
            )
            Text(
                text = "${puppy.ageInMonths} months",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultListPreview() {
    PuppyTheme {
        PuppyListScreen(
            PuppyList(
                filter = "abc",
                puppies = listOf(
                    Puppy(id = 1, name = "Baron", Gender.MALE, ageInMonths = 33),
                    Puppy(id = 2, name = "Apollo", Gender.MALE, ageInMonths = 2),
                    Puppy(id = 5, name = "sdfdsf", Gender.MALE, ageInMonths = 2),
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    PuppyTheme {
        PuppyDetailsScreen(
            PuppyDetails(
                Puppy(id = 1, name = "Baron", Gender.MALE, ageInMonths = 33)
            )
        )
    }
}