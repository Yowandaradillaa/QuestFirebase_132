package com.example.myfirebase.view.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.myfirebase.repositori.RepositorySiswa
import androidx.lifecycle.ViewModel
import com.example.myfirebase.modeldata.Siswa
import androidx.compose.runtime.getValue

sealed interface StatusUIDetail {
    data class Success(val siswa: Siswa) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    private val siswaId: Int = checkNotNull(savedStateHandle[DestinasiDetail.siswaIdArg])

    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set

    init {
        Log.d("DetailViewModel", "Init with siswaId: $siswaId")
        loadSiswa()
    }