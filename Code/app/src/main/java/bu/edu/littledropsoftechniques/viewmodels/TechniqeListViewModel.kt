package bu.edu.littledropsoftechniques.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import bu.edu.littledropsoftechniques.LittleDropsOfTechniquesApplication
import bu.edu.littledropsoftechniques.datalayer.Technique.Technique
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TechniqueListViewModel(application: Application): AndroidViewModel(application) {
    val littleDropsOfTechniquesRepository = (application as LittleDropsOfTechniquesApplication).littleDropsOfTechniquesRepository

    private lateinit var _techniqueList: LiveData<List<Technique>>

    val techniqueList:LiveData<List<Technique>>
        get() = _techniqueList

    fun setInitialTechniques() {

        val allTechniques = viewModelScope.async(Dispatchers.IO) {
            littleDropsOfTechniquesRepository.getAllTechniques()
        }
        viewModelScope.launch(Dispatchers.IO) {
            _techniqueList = allTechniques.await()
        }
    }

    fun addTechnique(technique: Technique) {
        viewModelScope.launch(Dispatchers.IO) {
            littleDropsOfTechniquesRepository.addTechnique(technique)
        }
    }
}