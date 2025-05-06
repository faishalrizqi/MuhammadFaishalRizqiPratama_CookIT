package com.muhammadfaishalrizqipratama0094.cookit.ui.screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.muhammadfaishalrizqipratama0094.cookit.model.Resep
import com.muhammadfaishalrizqipratama0094.cookit.viewmodel.ResepViewModel
import coil.compose.rememberAsyncImagePainter
import com.muhammadfaishalrizqipratama0094.cookit.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahResepScreen(
    navController: NavHostController,
    viewModel: ResepViewModel,
) {
    var nama by remember { mutableStateOf("") }
    var bahan by remember { mutableStateOf("") }
    var langkah by remember { mutableStateOf("") }
    var waktuMasak by remember { mutableStateOf("") }
    var selectedKategori by remember { mutableStateOf("") }
    val imageUri by remember { mutableStateOf<Uri?>(null) }

    val kategoriOptions = listOf(stringResource(R.string.makanan_ringan), stringResource(R.string.makanan_berat))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tambah_resep)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.kembali))
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                label = { Text(stringResource(R.string.nama_resep)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = bahan,
                onValueChange = { bahan = it },
                label = { Text(stringResource(R.string.bahan)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = langkah,
                onValueChange = { langkah = it },
                label = { Text(stringResource(R.string.langkah)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5
            )

            Text(stringResource(R.string.kategori), style = MaterialTheme.typography.bodyMedium)
            kategoriOptions.forEach { kategori ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (kategori == selectedKategori),
                            onClick = { selectedKategori = kategori }
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (kategori == selectedKategori),
                        onClick = null
                    )
                    Text(text = kategori, style = MaterialTheme.typography.bodyLarge)
                }
            }

            OutlinedTextField(
                value = waktuMasak,
                onValueChange = { waktuMasak = it },
                label = { Text(stringResource(R.string.waktu)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            val imagePainter = rememberAsyncImagePainter(
                model = imageUri ?: R.drawable.default_recipe_image
            )
            Image(
                painter = imagePainter,
                contentDescription = stringResource(R.string.gambar_resep),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Button(
                onClick = {
                    if (nama.isNotBlank() && bahan.isNotBlank() && langkah.isNotBlank() && selectedKategori.isNotBlank()) {
                        val resep = Resep(
                            nama = nama,
                            bahan = bahan,
                            langkah = langkah,
                            kategori = selectedKategori,
                            waktuMasak = waktuMasak.toIntOrNull() ?: 0,
                            tanggalDitambahkan = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                .format(Date()),
                            imageUrl = "android.resource://com.muhammadfaishalrizqipratama0094.cookit/drawable/default_recipe_image"
                        )
                        viewModel.tambahResep(resep)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nama.isNotBlank() && bahan.isNotBlank() && langkah.isNotBlank() && selectedKategori.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))
            ) {
                Text(stringResource(R.string.simpan))
            }
        }
    }
}

