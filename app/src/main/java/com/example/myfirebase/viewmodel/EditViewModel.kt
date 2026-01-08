package com.example.myfirebase.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirebase.modeldata.DetailSiswa
import com.example.myfirebase.modeldata.Siswa
import com.example.myfirebase.modeldata.UIStateSiswa
import com.example.myfirebase.modeldata.toUiStateSiswa
import com.example.myfirebase.repositori.RepositorySiswa
import com.example.myfirebase.view.route.DestinasiEdit
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    // ✅ FIX: String → Long (Navigation selalu String)
    private val siswaId: Long =
        checkNotNull(savedStateHandle[DestinasiEdit.itemIdArg])
            .toString()
            .toLong()

    init {
        Log.d("EditViewModel", "=== EDIT VIEWMODEL INITIALIZED ===")
        Log.d("EditViewModel", "SiswaId: $siswaId")
        viewModelScope.launch {
            loadSiswa()
        }
    }

    private suspend fun loadSiswa() {
        try {
            val siswa = repositorySiswa.getSatuSiswa(siswaId)
            uiStateSiswa = siswa.toUiStateSiswa(true)
        } catch (e: Exception) {
            Log.e("EditViewModel", "Error loading siswa", e)
        }
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean =
        uiState.nama.isNotBlank() &&
                uiState.alamat.isNotBlank() &&
                uiState.telpon.isNotBlank()

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    // ✅ FIX: benar-benar update ke repository
    suspend fun editSatuSiswa() {
        if (!validasiInput()) return

        try {
            val siswa = Siswa(
                id = siswaId,
                nama = uiStateSiswa.detailSiswa.nama,
                alamat = uiStateSiswa.detailSiswa.alamat,
                telpon = uiStateSiswa.detailSiswa.telpon
            )

            repositorySiswa.updateSiswa(siswa)
            Log.d("EditViewModel", "Update successful")
        } catch (e: Exception) {
            Log.e("EditViewModel", "Error updating", e)
            throw e
        }
    }
}
