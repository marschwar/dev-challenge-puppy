package de.codekenner.puppy

sealed class ApplicationViewState

data class PuppyList(
    val puppies: List<Puppy> = emptyList(),
    val filter: String = ""
) : ApplicationViewState()

data class PuppyDetails(val puppy: Puppy) : ApplicationViewState()
