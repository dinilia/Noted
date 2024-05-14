package com.example.noted.ui.screen.add_notes.components.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.noted.R

@Composable
fun NoteImage(
    modifier: Modifier = Modifier,
    bitmap: Bitmap? = null,
    onAddImageClick: () -> Unit = {}
) {
    Card(
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = modifier
            .width(150.dp)
            .height(150.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ) {
        Box(
            modifier = modifier
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            ImagePreviewItem(
                bitmap = bitmap ?: getPlaceholder(LocalContext.current),
                modifier = modifier.fillMaxSize(),
                onAddImageClick = onAddImageClick
            )
        }
    }
}

private fun getPlaceholder(context: Context): Bitmap {
    val db = ContextCompat.getDrawable(context, R.drawable.ic_image)
    val bit = Bitmap.createBitmap(
        db!!.intrinsicWidth, db.intrinsicHeight, Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bit)
    db.setBounds(0, 0, canvas.width, canvas.height)
    db.draw(canvas)
    return bit
}

@Composable
fun ImagePreviewItem(
    bitmap: Bitmap? = null,
    modifier: Modifier,
    onAddImageClick: () -> Unit
) {
    Column(modifier = Modifier) {
        AsyncImage(
            model = bitmap,
            modifier = modifier.clickable {
                onAddImageClick.invoke()
            },
            contentDescription = null,
        )
    }
}