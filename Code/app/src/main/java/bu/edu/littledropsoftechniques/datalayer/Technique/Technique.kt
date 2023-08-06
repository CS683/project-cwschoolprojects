package bu.edu.littledropsoftechniques.datalayer.Technique

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Entity(tableName = "techniques")
data class Technique(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var description: String,
    var authors:  Array<String>,
    var ingredients:  Array<String>,
    var steps:  Array<String>,
    var mainPhotoRef: String,
    var isLiked: Boolean,
    var tags:  Array<String>
)

class TechniquesArrayTypeConverter {

//    @TypeConverter
//    fun listToJson(value: List<Technique>?) = Gson().toJson(value)
//
//    @TypeConverter
//    fun jsonToList(value: String) = Gson().fromJson(value, Array<Technique>::class.java).toList()

    @TypeConverter
    fun fromString(value: String?): Array<String?>? {
        val listType: Type = object : TypeToken<Array<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: Array<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}