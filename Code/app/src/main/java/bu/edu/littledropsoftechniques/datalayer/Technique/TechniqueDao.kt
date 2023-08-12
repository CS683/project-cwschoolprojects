package bu.edu.littledropsoftechniques.datalayer.Technique

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TechniqueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTechnique(technique: Technique)

    @Delete
    fun delTechnique(technique: Technique)

    @Update
    fun editTechnique(technique: Technique)

    @Query("SELECT count(*) From techniques")
    fun count(): LiveData<Int>

    @Query("SELECT * FROM techniques")
    fun getAllTechniques(): LiveData<List<Technique>>

    @Query("SELECT * FROM techniques WHERE id = :techniqueId")
    fun searchTechnique(techniqueId: Long): LiveData<Technique>

    @Query("SELECT * FROM techniques WHERE title like :techniqueTitle")
    fun searchTechniquesByTitle(techniqueTitle:String): LiveData<List<Technique>>

    @Query("SELECT ingredients FROM techniques WHERE id = :techniqueId")
    fun getIngredientForTechnique(techniqueId: Long): LiveData<List<String>>

    @Query("SELECT steps FROM techniques WHERE id = :techniqueId")
    fun getStepsForTechnique(techniqueId: Long): LiveData<List<String>>

    @Query("SELECT tags FROM techniques WHERE id = :techniqueId")
    fun getTagsForTechnique(techniqueId: Long): LiveData<List<String>>
}