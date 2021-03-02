package de.codekenner.puppy

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.codekenner.puppy.ui.theme.PuppyTheme


@Composable
fun PuppyDetailsScreen(
    puppyDetails: PuppyDetails,
) {
    Text(text = puppyDetails.puppy.name, style = MaterialTheme.typography.h2)
}

@Preview(showBackground = true)
@Composable
fun DefaultDetailsPreview() {
    PuppyTheme {
        PuppyDetailsScreen(
            PuppyDetails(
                Puppy(id = 1, name = "Baron", Gender.MALE, ageInMonths = 33)
            )
        )
    }
}