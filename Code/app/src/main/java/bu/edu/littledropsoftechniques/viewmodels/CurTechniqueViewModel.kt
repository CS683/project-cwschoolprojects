package bu.edu.littledropsoftechniques.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bu.edu.littledropsoftechniques.LittleDropsOfTechniquesApplication
import bu.edu.littledropsoftechniques.datalayer.Technique.Technique

class CurTechniqueViewModel(application: Application): AndroidViewModel(application) {

    private val _curTechnique: MutableLiveData<Technique> = MutableLiveData()

    val curTechnique: LiveData<Technique>
        get() = _curTechnique

    val projectPortalRepository = (application as LittleDropsOfTechniquesApplication).littleDropsOfTechniquesRepository

    fun initCurTechnique(technique: Technique) {
        if (_curTechnique.value == null)
            _curTechnique.value = technique
    }

    fun setCurTechnique(technique: Technique) {
        if (technique != null)
            _curTechnique.value = technique
    }

    fun isCurTechnique(project: Technique): Boolean {
        return _curTechnique.value?.id == project.id
    }

    fun updateCurTechnique(
        title: String,
        description: String,
        authors: List<String>,
        ingredients: List<String>,
        steps: List<String>,
        mainPhotoRef: String,
        isLiked: Boolean,
        tags: List<String>
    ) {
        _curTechnique.value = _curTechnique.value?.apply {
            this.title = title
            this.description = description
            this.authors = authors
            this.ingredients = ingredients
            this.steps = steps
            this.mainPhotoRef = mainPhotoRef
            this.isLiked = isLiked
            this.tags = tags
        }

        projectPortalRepository.editTechnique(_curTechnique.value!!)
    }
}