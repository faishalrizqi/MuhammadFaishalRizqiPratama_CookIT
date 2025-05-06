package com.muhammadfaishalrizqipratama0094.cookit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.muhammadfaishalrizqipratama0094.cookit.database.ResepDb
import com.muhammadfaishalrizqipratama0094.cookit.model.Resep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ResepViewModel(application: Application) : AndroidViewModel(application) {
    private val resepDao = ResepDb.getInstance(application).dao

    val semuaResep = resepDao.getAllResep()

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
            resepDao.delete(resep)
        }
    }

    fun cariResep(query: String): Flow<List<Resep>> {
        return resepDao.searchResep(query)
    }

    suspend fun getResepById(id: Long): Resep? {
        return resepDao.getResepById(id)
    }
}