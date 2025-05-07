package com.muhammadfaishalrizqipratama0094.cookit.ui.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.muhammadfaishalrizqipratama0094.cookit.R
import com.muhammadfaishalrizqipratama0094.cookit.model.Resep
import com.muhammadfaishalrizqipratama0094.cookit.navigation.Screen
import com.muhammadfaishalrizqipratama0094.cookit.ui.components.ConfirmDialog
import com.muhammadfaishalrizqipratama0094.cookit.ui.components.ResepItem
import com.muhammadfaishalrizqipratama0094.cookit.viewmodel.ResepViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: ResepViewModel
) {
    val isList by viewModel.layoutPreference.collectAsState(initial = true)
    val semuaResep by viewModel.semuaResep.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    val resepToDelete by remember { mutableStateOf<Resep?>(null) }
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = {
                        viewModel.setLayoutPreference(!isList)
                    }) {
                        Icon(
                            painter = painterResource(
                                if (isList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_view_list_24,
                            ),
                            contentDescription = stringResource(R.string.toogle_layout),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = stringResource(R.string.more_options),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.recycle_bin)) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                },
                                onClick = {
                                    navController.navigate(Screen.RecycleBin.route)
                                    showMenu = false
                                }
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.TambahResep.route)
                },
                containerColor = if (isSystemInDarkTheme()) {
                    Color(0xFFE65100)
                } else {
                    Color(0xFFFF9800)
                },
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.tambah_resep)
                )
            }
        }
    ) { padding ->
        if (showDialog && resepToDelete != null) {
            ConfirmDialog(
                title = stringResource(R.string.hapus_resep),
                message = stringResource(R.string.pesan),
                onDismiss = { showDialog = false },
                onConfirm = {
                    viewModel.hapusResep(resepToDelete!!)
                    showDialog = false
                }
            )
        }

        if (semuaResep.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.list_kosong),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    stringResource(R.string.list_kosong_keterangan),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            if (isList) {
                LazyColumn(
                    modifier = Modifier.padding(padding)
                ) {
                    items(semuaResep) { resep ->
                        ResepItem(
                            resep = resep,
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                navController.navigate(Screen.DetailResep.withId(resep.id))
                            }
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(semuaResep) { resep ->
                        ResepItem(
                            resep = resep,
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                navController.navigate(Screen.DetailResep.withId(resep.id))
                            }
                        )
                    }
                }
            }
        }
    }
}