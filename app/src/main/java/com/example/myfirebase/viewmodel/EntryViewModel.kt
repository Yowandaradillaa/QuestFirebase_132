package com.example.myfirebase.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myfirebase.modeldata.DetailSiswa
import com.example.myfirebase.modeldata.UIStateSiswa
import com.example.myfirebase.repositori.RepositorySiswa
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.myfirebase.modeldata.toDataSiswa

class EntryViewModel(
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private fun validasiInput(
        uiState: DetailSiswa = uiStateSiswa.detailSiswa
    ): Boolean {
        return with(uiState) {
            nama.isNotBlank() &&
                    alamat.isNotBlank() &&
                    telpon.isNotBlank()
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    suspend fun addSiswa() {
        if (validasiInput()) {
            try {
                repositorySiswa.postDataSiswa(
                    uiStateSiswa.detailSiswa.toDataSiswa()
                )
                Log.d("EntryViewModel", "Data berhasil disimpan")
            } catch (e: Exception) {
                Log.e("EntryViewModel", "Error saat menyimpan data: ${e.message}")
                throw e // Re-throw untuk di-handle di UI
            }
        }
    }
}