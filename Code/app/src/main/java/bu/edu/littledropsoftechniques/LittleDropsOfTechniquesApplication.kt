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
                    "Royal Icing",
                    "What is royal icing and what is is good for?",
                    listOf("System Admin"),
                    listOf(" 1/2 cup water",
                        "2 lbs powdered sugar",
                        "1 tsp cream of tartar"),
                    listOf("Mix all ingredients together in a mixer."),
                    "https://www.sweetsugarbelle.com/blog/wp-content/uploads/2011/06/Finished+product.jpg",
                    false,
                    listOf("royal icing", "icing")
                )
            )
            littleDropsOfTechniquesDatabase.techniqueDao().addTechnique(
                Technique(
                    0,
                    "Piping Royal Icing",
                    "How to pip royal icing.",
                    listOf("System Admin"),
                    listOf(" 1/2 cup water",
                        "2 lbs powdered sugar",
                        "1 tsp cream of tartar"),
                    listOf("Mix all ingredients together in a mixer."),
                    "https://www.thefrostedkitchen.com/wp-content/uploads/2021/02/Flood-Consistency.png",
                    false,
                    listOf("royal icing", "icing")
                )
            )
            littleDropsOfTechniquesDatabase.techniqueDao().addTechnique(
                Technique(
                    0,
                    "Fondant",
                    "What is fondant and what is is good for?",
                    listOf("System Admin"),
                    listOf("16 ounces mini marshmallows",
                            "32 ounces powdered sugar",
                            "4 tablespoons water",
                            "1 teaspoon shortening"),
                    listOf("Put marshmallows, water, and shortening in a glass microwavable bowl.",
                        "Microwave for 20 seconds, then stir. Repeat 4 times.",
                        "Pour into a mixer and mix well using the dough attachment. Add in powdered sugar a cup at a time. Mix until the shine is completely gone. You may not need to use all the sugar, just keep adding until it has the consistency of play dough. The final result should not be sticky to the touch."),
                    "https://www.biggerbolderbaking.com/wp-content/uploads/2016/04/IMG_1868.jpg",
                    false,
                    listOf("icing")
                )
            )
        }
    }
}