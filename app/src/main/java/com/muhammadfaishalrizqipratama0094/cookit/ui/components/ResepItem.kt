package com.muhammadfaishalrizqipratama0094.cookit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.muhammadfaishalrizqipratama0094.cookit.R
import com.muhammadfaishalrizqipratama0094.cookit.model.Resep

@Composable
fun ResepItem(
    resep: Resep,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val imagePainter = rememberAsyncImagePainter(
        model = resep.imageUrl
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = imagePainter,
                contentDescription = stringResource(R.string.gambar_resep),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = resep.nama,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = stringResource(R.string.list_waktu) + " ${resep.waktuMasak}" + stringResource(R.string.menit),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = stringResource(R.string.kategori) + ": ${resep.kategori}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
