package com.example.myfirebase.view.viewmodel

import androidx.compose.runtime.mutableStateSetOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirebase.modeldata.Siswa
import com.example.myfirebase.repositori.RepositorySiswa
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

sealed interface StatusUiSiswa{
    data class Success(val siswa : List<Siswa> = listOf()) : StatusUiSiswa
    object Error : StatusUiSiswa
    object Loading : StatusUiSiswa
}

class HomeViewModel(private val repositorySiswa: RepositorySiswa): ViewModel(){
    var statusUiSiswa: StatusUiSiswa by mutableStateSetOf(StatusUiSiswa.Loading)
        private set
    init{
        loadSiswa()
    }

    fun loadSiswa(){
        viewModelScope.launch {
            statusUiSiswa = StatusUiSiswa.Loading
            statusUiSiswa = try {
                StatusUiSiswa.Success(repositorySiswa.getDataSiswa())
            }catch (e: IOException){
                StatusUiSiswa.Error
            }catch (e: Exception){
                StatusUiSiswa.Error
            }
        }
    }

}