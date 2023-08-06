package bu.edu.littledropsoftechniques.viewmodels

import android.app.Application
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

    fun setInitialProjects() {
        val allProjects = viewModelScope.async(Dispatchers.IO) {
            littleDropsOfTechniquesRepository.getAllTechniques()
        }
        viewModelScope.launch(Dispatchers.IO) {
            _techniqueList = allProjects.await()
        }
    }

    fun addTechnique(technique: Technique) {
        viewModelScope.launch(Dispatchers.IO) {
            littleDropsOfTechniquesRepository.addTechnique(technique)
        }
    }
}