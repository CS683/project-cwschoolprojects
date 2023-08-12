package bu.edu.littledropsoftechniques.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import bu.edu.littledropsoftechniques.LittleDropsOfTechniquesApplication
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class StringsListViewModel(application: Application): AndroidViewModel(application) {
    val littleDropsOfTechniquesRepository = (application as LittleDropsOfTechniquesApplication).littleDropsOfTechniquesRepository

    private lateinit var _ingredientsList: LiveData<List<String>>
    private lateinit var _stepsList: LiveData<List<String>>
    private lateinit var _tagsList: LiveData<List<String>>

    val ingredientsList:LiveData<List<String>>
        get() = _ingredientsList

    val stepsList:LiveData<List<String>>
        get() = _stepsList

    val tagsList:LiveData<List<String>>
        get() = _tagsList

    fun setTechniqueIngredientsAndSteps(techniqueId: Long) {

        val allIngredients = viewModelScope.async(Dispatchers.IO) {
            littleDropsOfTechniquesRepository.getIngredientForTechnique(techniqueId)
        }
        viewModelScope.launch(Dispatchers.IO) {
            _ingredientsList = allIngredients.await()
        }

        val allSteps = viewModelScope.async(Dispatchers.IO) {
            littleDropsOfTechniquesRepository.getStepsForTechnique(techniqueId)
        }
        viewModelScope.launch(Dispatchers.IO) {
            _stepsList = allSteps.await()
        }

        val allTags = viewModelScope.async(Dispatchers.IO) {
            littleDropsOfTechniquesRepository.getTagsForTechnique(techniqueId)
        }
        viewModelScope.launch(Dispatchers.IO) {
            _tagsList = allTags.await()
        }
    }

//    fun addTechnique(technique: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            littleDropsOfTechniquesRepository.addTechnique(technique)
//        }
//    }
}