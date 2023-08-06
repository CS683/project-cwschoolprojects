package bu.edu.littledropsoftechniques

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import bu.edu.littledropsoftechniques.datalayer.Technique.LittleDropsOfTechniquesDatabase
import bu.edu.littledropsoftechniques.datalayer.Technique.LittleDropsOfTechniquesRepository
import bu.edu.littledropsoftechniques.datalayer.Technique.Technique
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LittleDropsOfTechniquesApplication: Application() {

    lateinit var littleDropsOfTechniquesDatabase: LittleDropsOfTechniquesDatabase
    lateinit var littleDropsOfTechniquesRepository: LittleDropsOfTechniquesRepository

    override fun onCreate() {
        super.onCreate()
        littleDropsOfTechniquesDatabase =
            Room.databaseBuilder(
                applicationContext, LittleDropsOfTechniquesDatabase::class.java,
                "littledropsoftechniques-db"
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        addInitTechniques()
                    }
                })
                .build()
        littleDropsOfTechniquesRepository = LittleDropsOfTechniquesRepository(littleDropsOfTechniquesDatabase.techniqueDao())
    }

    fun addInitTechniques(){

        CoroutineScope(Dispatchers.IO).launch {
            littleDropsOfTechniquesDatabase.techniqueDao().addTechnique(
                Technique(
                    0,
                    "First Technique",
                    "This is a test",
                    listOf("Cherylee Wells"),
                    listOf("Test"),
                    listOf("Test"),
                    "url",
                    false,
                    listOf("Test")
                )
            )
            littleDropsOfTechniquesDatabase.techniqueDao().addTechnique(
                Technique(
                    0,
                    "Second Technique",
                    "This is a test",
                    listOf("Cherylee Wells"),
                    listOf("Test"),
                    listOf("Test"),
                    "url",
                    false,
                    listOf("Test")
                )
            )
            littleDropsOfTechniquesDatabase.techniqueDao().addTechnique(
                Technique(
                    0,
                    "Third Technique",
                    "This is a test",
                    listOf("Cherylee Wells"),
                    listOf("Test"),
                    listOf("Test"),
                    "url",
                    false,
                    listOf("Test")
                )
            )
        }
    }
}