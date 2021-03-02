package de.codekenner.puppy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.codekenner.puppy.ui.theme.PuppyTheme


@Composable
fun PuppyListScreen(
    puppyList: PuppyList,
    onViewEvent: (ViewEvent) -> Unit = {}
) {
    Column {
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.h2
        )
        SearchBar(
            puppyList.filter,
            onSearchTermChange = { value -> onViewEvent(FilterChanged(value)) }
        )
        Puppies(puppyList.puppies, onItemSelected = { value -> onViewEvent(PuppySelected(value)) })
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
fun Puppies(puppies: List<Puppy>, onItemSelected: (Puppy) -> Unit) {
    LazyColumn {
        items(puppies, key = Puppy::id) { puppy ->
            Puppy(puppy, onItemSelected)
        }
    }
}

@Composable
fun Puppy(puppy: Puppy, onClick: (Puppy) -> Unit) {
    Row(modifier = Modifier
        .clickable { onClick(puppy) }
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
                    Puppy(id = 5, name = "Tiger", Gender.MALE, ageInMonths = 6),
                )
            )
        )
    }
}