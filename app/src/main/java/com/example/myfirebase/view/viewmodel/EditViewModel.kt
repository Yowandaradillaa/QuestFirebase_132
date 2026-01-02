package com.example.myfirebase.view.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.myfirebase.repositori.RepositorySiswa
import androidx.lifecycle.ViewModel
import com.example.myfirebase.modeldata.UIStateSiswa
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.example.myfirebase.modeldata.toUiStateSiswa
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private val siswaId: Int = checkNotNull(savedStateHandle[DestinasiEdit.siswaIdArg])

    init {
        Log.d("EditViewModel", "=== EDIT VIEWMODEL INITIALIZED ===")
        Log.d("EditViewModel", "SiswaId: $siswaId")
        viewModelScope.launch {
            loadSiswa()
        }
    }

    private suspend fun loadSiswa() {
        try {
            Log.d("EditViewModel", "Loading siswa with ID: $siswaId")
            val siswa = repositorySiswa.getSatuSiswa(siswaId.toLong())
            Log.d("EditViewModel", "Loaded siswa: ${siswa.nama}")

            uiStateSiswa = siswa.toUiStateSiswa(true)
        } catch (e: Exception) {
            Log.e("EditViewModel", "Error loading siswa: ${e.message}", e)
        }
    }

