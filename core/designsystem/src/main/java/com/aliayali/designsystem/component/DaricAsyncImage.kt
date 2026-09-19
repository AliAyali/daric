package com.aliayali.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter

@Composable
fun DaricAsyncImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
    )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}