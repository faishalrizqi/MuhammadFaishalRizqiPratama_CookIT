package com.muhammadfaishalrizqipratama0094.cookit.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object TambahResep : Screen("tambah_resep")
    data object DetailResep : Screen("detail_resep/{resepId}") {
        fun withId(id: Long): String {
            return "detail_resep/$id"
        }
    }
    data object EditResep : Screen("edit_resep/{resepId}") {
        fun withId(id: Long): String {
            return "edit_resep/$id"
        }
    }
    data object RecycleBin : Screen("recycle_bin")
    data object ThemeSettings : Screen("theme_settings")
}