package com.muhammadfaishalrizqipratama0094.cookit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.muhammadfaishalrizqipratama0094.cookit.database.ResepDb
import com.muhammadfaishalrizqipratama0094.cookit.model.Resep
import com.muhammadfaishalrizqipratama0094.cookit.util.SettingsDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResepViewModel(application: Application) : AndroidViewModel(application) {
    private val resepDao = ResepDb.getInstance(application).dao
    val semuaResep = resepDao.getAllResep()
    val recycledResep = resepDao.getRecycledResep()

    private val settingsDataStore = SettingsDataStore(application)
    val layoutPreference = settingsDataStore.layoutPreference

    fun setLayoutPreference(isList: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataStore.saveLayoutPreference(isList)
        }
    }

    fun tambahResep(resep: Resep) {
        viewModelScope.launch(Dispatchers.IO) {
            resepDao.insert(resep)
        }
    }

    fun updateResep(resep: Resep) {
        viewModelScope.launch(Dispatchers.IO) {
            resepDao.update(resep)
        }
    }

    fun hapusResep(resep: Resep) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            resepDao.moveToRecycleBin(resep.id, currentDate)
        }
    }

    fun restoreResep(resep: Resep) {
        viewModelScope.launch(Dispatchers.IO) {
            resepDao.restoreFromRecycleBin(resep.id)
        }
    }

    fun permanentlyDeleteResep(resep: Resep) {
        viewModelScope.launch(Dispatchers.IO) {
            resepDao.permanentlyDelete(resep.id)
        }
    }

    fun emptyRecycleBin() {
        viewModelScope.launch(Dispatchers.IO) {
            resepDao.emptyRecycleBin()
        }
    }

    suspend fun getResepById(id: Long): Resep? {
        return resepDao.getResepById(id)
    }
}