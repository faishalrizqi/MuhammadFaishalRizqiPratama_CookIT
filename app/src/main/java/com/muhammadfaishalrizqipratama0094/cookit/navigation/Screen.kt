package com.muhammadfaishalrizqipratama0094.cookit.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object TambahResep : Screen("tambah_resep")
    data object DetailResep : Screen("detail_resep/{resepId}") {
        fun withId(resepId: Long) = "detail_resep/$resepId"
    }

    data object EditResep : Screen("edit_resep/{resepId}") {
        fun withId(resepId: Long) = "edit_resep/$resepId"
    }
}