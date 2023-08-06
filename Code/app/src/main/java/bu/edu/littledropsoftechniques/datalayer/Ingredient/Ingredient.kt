package bu.edu.littledropsoftechniques.datalayer.Ingredient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val num: Int,
    var instruction: String
)