package bu.edu.littledropsoftechniques.datalayer.Technique

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LittleDropsOfTechniquesRepository (
    private val techniqueDao: TechniqueDao,
) {
    fun addTechnique(technique: Technique){
        CoroutineScope(Dispatchers.IO).launch{
            techniqueDao.addTechnique(technique)
        }
    }

    fun delTechnique(technique: Technique) {
        CoroutineScope(Dispatchers.IO).launch{
            techniqueDao.delTechnique(technique)
        }
    }

    fun editTechnique(technique: Technique) {
        CoroutineScope(Dispatchers.IO).launch{
            techniqueDao.editTechnique(technique)
        }
    }

    suspend fun getAllTechniques(): LiveData<List<Technique>> {
        return techniqueDao.getAllTechniques()
    }

    suspend fun searchTechnique(projId: Long): LiveData<Technique> {
        return techniqueDao.searchTechnique(projId)
    }

    suspend fun searchTechniquesByTitle(projTitle:String): LiveData<List<Technique>> {
        return techniqueDao.searchTechniquesByTitle(projTitle)
    }

    suspend fun getIngredientForTechnique(techniqueId: Long): LiveData<List<String>> {
        return techniqueDao.getIngredientForTechnique(techniqueId)
    }

    suspend fun getStepsForTechnique(techniqueId: Long): LiveData<List<String>> {
        return techniqueDao.getStepsForTechnique(techniqueId)
    }

    suspend fun getTagsForTechnique(techniqueId: Long): LiveData<List<String>> {
        return techniqueDao.getTagsForTechnique(techniqueId)
    }

    suspend fun count(): LiveData<Int> {
        return techniqueDao.count()
    }
}