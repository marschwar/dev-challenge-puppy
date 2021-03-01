package de.codekenner.puppy

import android.media.Image

sealed class ImageLoadingProgress
object Loading : ImageLoadingProgress()
object Error : ImageLoadingProgress()
data class Success(val image: Image) : ImageLoadingProgress()
