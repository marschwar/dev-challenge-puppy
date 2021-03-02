package de.codekenner.puppy

sealed class ViewState

data class PuppyList(
    val puppies: List<Puppy> = emptyList(),
    val filter: String = ""
) : ViewState()

data class PuppyDetails(val puppy: Puppy) : ViewState()
