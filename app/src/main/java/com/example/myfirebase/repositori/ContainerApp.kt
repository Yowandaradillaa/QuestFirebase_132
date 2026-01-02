package com.example.myfirebase.repositori

import kotlin.getValue
import android.app.Application

interface ContainerApp{
    val repositorySiswa : RepositorySiswa
}

class DefaultContainerApp : ContainerApp{
    override val repositorySiswa: RepositorySiswa by lazy {
        FirebaseRepositorySiswa()
    }
}

class AplikasiDataSiswa : Aplication(){
    lateinit var container: ContainerApp
    override fun onCreate(){
        super.onCreate()
        this.container = DefaultContainerApp()
    }
}