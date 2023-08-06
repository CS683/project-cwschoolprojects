package bu.edu.littledropsoftechniques.datalayer.Technique

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Technique::class],
    version = 1
)

@TypeConverters(TechniquesArrayTypeConverter::class)
abstract class LittleDropsOfTechniquesDatabase: RoomDatabase() {
    abstract fun techniqueDao(): TechniqueDao
}