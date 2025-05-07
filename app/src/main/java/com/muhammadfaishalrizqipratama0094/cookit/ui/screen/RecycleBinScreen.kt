package com.muhammadfaishalrizqipratama0094.cookit.ui.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.muhammadfaishalrizqipratama0094.cookit.R
import com.muhammadfaishalrizqipratama0094.cookit.model.Resep
import com.muhammadfaishalrizqipratama0094.cookit.ui.components.ConfirmDialog
import com.muhammadfaishalrizqipratama0094.cookit.ui.components.RecycledResepItem
import com.muhammadfaishalrizqipratama0094.cookit.viewmodel.ResepViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(
    navController: NavHostController,
    viewModel: ResepViewModel
) {
    val recycledResep by viewModel.recycledResep.collectAsState(initial = emptyList())
    var showEmptyBinDialog by remember { mutableStateOf(false) }
    var showPermanentDeleteDialog by remember { mutableStateOf(false) }
    var resepToDelete by remember { mutableStateOf<Resep?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.recycle_bin)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                actions = {
                    if (recycledResep.isNotEmpty()) {
                        IconButton(onClick = { showEmptyBinDialog = true }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(R.string.empty_bin),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
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
        if (showEmptyBinDialog) {
            ConfirmDialog(
                title = stringResource(R.string.empty_bin),
                message = stringResource(R.string.empty_bin_message),
                onDismiss = { showEmptyBinDialog = false },
                onConfirm = {
                    viewModel.emptyRecycleBin()
                    showEmptyBinDialog = false
                }
            )
        }

        if (showPermanentDeleteDialog && resepToDelete != null) {
            ConfirmDialog(
                title = stringResource(R.string.permanent_delete),
                message = stringResource(R.string.permanent_delete_message),
                onDismiss = {
                    showPermanentDeleteDialog = false
                    resepToDelete = null
                },
                onConfirm = {
                    resepToDelete?.let { viewModel.permanentlyDeleteResep(it) }
                    showPermanentDeleteDialog = false
                    resepToDelete = null
                }
            )
        }

        if (recycledResep.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.recycle_bin_empty), style = MaterialTheme.typography.titleMedium)
                Text(stringResource(R.string.recycle_bin_empty_details), style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {
                items(recycledResep) { resep ->
                    RecycledResepItem(
                        resep = resep,
                        modifier = Modifier.padding(8.dp),
                        onRestore = { viewModel.restoreResep(resep) },
                        onDelete = {
                            resepToDelete = resep
                            showPermanentDeleteDialog = true
                        }
                    )
                }
            }
        }
    }
}