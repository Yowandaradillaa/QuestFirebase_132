package com.example.myfirebase.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirebase.modeldata.Siswa
import com.example.myfirebase.repositori.RepositorySiswa
import com.example.myfirebase.view.route.DestinasiDetail
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface StatusUIDetail {
    data class Success(val siswa: Siswa) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    // ✅ BENAR: ambil dari Navigation sebagai String → Long
    private val siswaId: Long =
        checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])
            .toString()
            .toLong()

    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set

    init {
        Log.d("DetailViewModel", "Init with siswaId: $siswaId")
        loadSiswa()
    }

    fun loadSiswa() {
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.Loading
            statusUIDetail = try {
                val siswaList = repositorySiswa.getDataSiswa()
                val siswa = siswaList.find { it.id == siswaId }

                if (siswa != null) {
                    StatusUIDetail.Success(siswa)
                } else {
                    Log.e("DetailViewModel", "Siswa with ID $siswaId not found")
                    StatusUIDetail.Error
                }
            } catch (e: IOException) {
                Log.e("DetailViewModel", "IOException", e)
                StatusUIDetail.Error
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Exception", e)
                StatusUIDetail.Error
            }
        }
    }

    suspend fun hapusSatuSiswa() {
        viewModelScope.launch {
            try {
                Log.d("DetailViewModel", "Deleting siswa with ID: $siswaId")
                repositorySiswa.deleteSiswa(siswaId)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Delete error", e)
            }
        }.join()
    }
}
