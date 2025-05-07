package com.muhammadfaishalrizqipratama0094.cookit.ui.screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
    var namaError by remember { mutableStateOf(false) }

    var bahan by remember { mutableStateOf("") }
    var bahanError by remember { mutableStateOf(false) }

    var langkah by remember { mutableStateOf("") }
    var langkahError by remember { mutableStateOf(false) }

    var waktuMasak by remember { mutableStateOf("") }
    var waktuMasakError by remember { mutableStateOf(false) }

    var selectedKategori by remember { mutableStateOf("") }
    var kategoriError by remember { mutableStateOf(false) }

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
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                label = { Text(stringResource(R.string.nama_resep)) },
                modifier = Modifier.fillMaxWidth(),
                isError = namaError,
                supportingText = {
                    if (namaError) {
                        Text(stringResource(R.string.invalid_nama))
                    }
                },
                trailingIcon = {
                    if (namaError) {
                        Icon(Icons.Filled.Warning, contentDescription = null)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = bahan,
                onValueChange = { bahan = it },
                label = { Text(stringResource(R.string.bahan)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                isError = bahanError,
                supportingText = {
                    if (bahanError) {
                        Text(stringResource(R.string.invalid_bahan))
                    }
                },
                trailingIcon = {
                    if (bahanError) {
                        Icon(Icons.Filled.Warning, contentDescription = null)
                    }
                }
            )

            OutlinedTextField(
                value = langkah,
                onValueChange = { langkah = it },
                label = { Text(stringResource(R.string.langkah)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5,
                isError = langkahError,
                supportingText = {
                    if (langkahError) {
                        Text(stringResource(R.string.invalid_langkah))
                    }
                },
                trailingIcon = {
                    if (langkahError) {
                        Icon(Icons.Filled.Warning, contentDescription = null)
                    }
                }
            )

            Column {
                Text(
                    stringResource(R.string.kategori),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (kategoriError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )

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

                if (kategoriError) {
                    Text(
                        text = stringResource(R.string.invalid_kategori),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            OutlinedTextField(
                value = waktuMasak,
                onValueChange = { waktuMasak = it },
                label = { Text(stringResource(R.string.waktu)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                isError = waktuMasakError,
                supportingText = {
                    if (waktuMasakError) {
                        Text(stringResource(R.string.invalid_waktu))
                    }
                },
                trailingIcon = {
                    if (waktuMasakError) {
                        Icon(Icons.Filled.Warning, contentDescription = null)
                    }
                }
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
                    namaError = nama.isBlank()
                    bahanError = bahan.isBlank()
                    langkahError = langkah.isBlank()
                    kategoriError = selectedKategori.isBlank()
                    waktuMasakError = waktuMasak.isBlank() || waktuMasak.toIntOrNull() == null || waktuMasak.toIntOrNull() == 0

                    if (!(namaError || bahanError || langkahError || kategoriError || waktuMasakError)) {
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(stringResource(R.string.simpan))
            }
        }
    }
}