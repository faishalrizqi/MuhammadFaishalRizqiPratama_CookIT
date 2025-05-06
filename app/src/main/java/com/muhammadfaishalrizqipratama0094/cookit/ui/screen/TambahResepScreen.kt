package com.muhammadfaishalrizqipratama0094.cookit.ui.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.muhammadfaishalrizqipratama0094.cookit.R
import com.muhammadfaishalrizqipratama0094.cookit.model.Resep
import com.muhammadfaishalrizqipratama0094.cookit.viewmodel.ResepViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahResepScreen(
    navController: NavHostController,
    viewModel: ResepViewModel
) {
    var nama by remember { mutableStateOf("") }
    var bahan by remember { mutableStateOf("") }
    var langkah by remember { mutableStateOf("") }
    var waktuMasak by remember { mutableStateOf("") }

    val kategoriOptions = listOf(stringResource(R.string.makanan_ringan), stringResource(R.string.makanan_berat))
    var selectedKategori by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.tambah_resep_baru)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali))
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        Color(0xFFE65100)
                    } else {
                        Color(0xFFFF9800)
                    },
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

            Text(
                text = stringResource(R.string.kategori),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Column {
                kategoriOptions.forEach { kategori ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (kategori == selectedKategori),
                                onClick = { selectedKategori = kategori },
                                role = Role.RadioButton
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (kategori == selectedKategori),
                            onClick = null
                        )
                        Text(
                            text = kategori,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            OutlinedTextField(
                value = waktuMasak,
                onValueChange = { waktuMasak = it },
                label = { Text(stringResource(R.string.waktu)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                                .format(Date())
                        )
                        viewModel.tambahResep(resep)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nama.isNotBlank() && bahan.isNotBlank() && langkah.isNotBlank() && selectedKategori.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        Color(0xFFE65100)
                    } else {
                        Color(0xFFFF9800)
                    },
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.6f),
                    disabledContentColor = Color.Gray
                )
            ) {
                Text(stringResource(R.string.simpan))
            }
        }
    }
}