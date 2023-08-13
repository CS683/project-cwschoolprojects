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
    private lateinit var _addIngredientsList: MutableList<String>
    private lateinit var _addStepsList: MutableList<String>
    private lateinit var _addTagsList: MutableList<String>

    val ingredientsList:LiveData<List<String>>
        get() = _ingredientsList

    val stepsList:LiveData<List<String>>
        get() = _stepsList

    val tagsList:LiveData<List<String>>
        get() = _tagsList

    val addIngredientsList:MutableList<String>
        get() = _addIngredientsList

    val addStepsList:MutableList<String>
        get() = _addStepsList

    val addTagsList:MutableList<String>
        get() = _addTagsList

    fun setTechniqueIngredientsAndSteps(techniqueId: Long) {

        // In this case we are showing an existing technique
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

        // In this case we are adding a new technique
        _addIngredientsList = mutableListOf()
        _addStepsList = mutableListOf()
        _addTagsList = mutableListOf()
    }

    fun addIngredient(ingredient: String) {
        _addIngredientsList.add(ingredient)
    }

    fun addStep(step: String) {
        _addStepsList.add(step)
    }

    fun addTag(tag: String) {
        _addTagsList.add(tag)
    }

    fun deleteIngredient(ingredient: String) {
        _addIngredientsList.remove(ingredient)
    }

    fun deleteStep(step: String) {
        _addStepsList.remove(step)
    }

    fun deleteTag(tag: String) {
        _addTagsList.remove(tag)
    }
}