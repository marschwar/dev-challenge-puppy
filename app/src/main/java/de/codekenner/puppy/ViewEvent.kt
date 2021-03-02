package de.codekenner.puppy

sealed class ViewEvent
data class FilterChanged(val filter: String) : ViewEvent()
data class PuppySelected(val puppy: Puppy) : ViewEvent()