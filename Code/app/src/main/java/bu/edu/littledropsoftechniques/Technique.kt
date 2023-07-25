package bu.edu.littledropsoftechniques

data class Technique(val id: Int,
                     var title: String,
                     var description: String,
                     var authors: String,
                     var ingredients: MutableList<String>,
                     var steps: MutableList<String>,
                     var mainPhotoRef: String,
                     var isLiked: Boolean,
                     var tags: MutableList<String>
){
    companion object {
        private val technique0 = Technique(
            0,
            "First Technique",
            "This is a test",
            "Cherylee Wells",
            mutableListOf("Test"),
            mutableListOf("Test"),
            "url",
            false,
            mutableListOf("Test")
            )
        private val technique1 = Technique(
            1,
            "Second Technique",
            "This is a test",
            "Cherylee Wells",
            mutableListOf("Test"),
            mutableListOf("Test"),
            "url",
            false,
            mutableListOf("Test")
        )
        private val technique2 = Technique(
            2,
            "Third Technique",
            "This is a test",
            "Cherylee Wells",
            mutableListOf("Test"),
            mutableListOf("Test"),
            "url",
            false,
            mutableListOf("Test")
        )
        val techniques = mutableListOf(technique0, technique1, technique2)
    }
}