package com.example.masterand

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.masterand.repositories.AppContainer
import com.example.masterand.repositories.AppDataContainer
import com.example.masterand.view_models.MainGameViewModel
import com.example.masterand.view_models.ProfileViewModel
import com.example.masterand.view_models.ResultScreenViewModel

class MasterAndApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProfileViewModel(masterAndApplication().container.playersRepository)
        }
        initializer {
            MainGameViewModel(masterAndApplication().container.scoreRepository)
        }
        initializer {
            ResultScreenViewModel(
                masterAndApplication().container.playerScoreRepository
            )
        }
    }
}
fun CreationExtras.masterAndApplication(): MasterAndApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
            MasterAndApplication)