package bu.edu.littledropsoftechniques.datalayer.Technique

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

@Entity(tableName = "techniques")
data class Technique(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var description: String,
    var authors:  List<String>,
    var ingredients:  List<String>,
    var steps:  List<String>,
    var mainPhotoRef: String,
    var isLiked: Boolean,
    var tags:  List<String>
)

class TechniquesTypeConverter {

    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}