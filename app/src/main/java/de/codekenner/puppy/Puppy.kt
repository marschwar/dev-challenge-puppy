package de.codekenner.puppy

data class Puppy(val id: Int, val name: String, val gender: Gender, val ageInMonths: Int, val imageUrl: String = "")
