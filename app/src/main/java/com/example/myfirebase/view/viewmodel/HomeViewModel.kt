package com.example.myfirebase.view.viewmodel

import androidx.compose.runtime.mutableStateSetOf
import androidx.lifecycle.ViewModel
import com.example.myfirebase.modeldata.Siswa
import com.example.myfirebase.repositori.RepositorySiswa

sealed interface StatusUiSiswa{
    data class Success(val siswa : List<Siswa> = listOf()) : StatusUiSiswa
    object Error : StatusUiSiswa
    object Loading : StatusUiSiswa
}

class HomeViewModel(private val repositorySiswa: RepositorySiswa): ViewModel(){
    var StatusUiSiswa: StatusUiSiswa by mutableStateSetOf(StatusUiSiswa.Loading)
        private set
    init{
        loadSiswa()
    }


}