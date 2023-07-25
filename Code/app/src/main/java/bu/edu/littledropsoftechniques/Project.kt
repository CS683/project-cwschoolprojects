package bu.edu.littledropsoftechniques

data class Project(val id: Int,
                   var title: String,
                   var description: String,
                   var authors: String,
                   var urls: String,
                   var isLiked: Boolean,
                   var keywords: String,
                   var dateCreated: String
    ){
    companion object {
        val project0 = Project(
            0,
            "Weather Forecast",
            "Weather Forecast is an app ...",
            "Jane Doe, Harry Styles",
            "https://www.youtube.com/weatherforecast",
            false,
            "weather, forecast",
            "2020/03/07"
            )
        val project1 = Project(
            1,
            "Connect Me",
            "Connect Me is an app ...",
            "Alexa Turner, Sally Turner",
            "https://www.facebook.com/connectme",
            false,
            "connect",
            "2021/01/07"
        )
        val project2 = Project(
            2,
            "What to Eat",
            "What to Eat is an app ...",
            "Julia Styles",
            "https://www.whattoeat.com/home",
            false,
            "food, eat",
            "2023/03/05"
        )
        val project3 = Project(
            3,
            "Project Portal",
            "Project Portal is an app ...",
            "Cherylee Wells",
            "https://www.projectportal.com/cherylee",
            true,
            "projects",
            "2023/07/03"
        )
        val projects = mutableListOf(project0, project1, project2, project3)
    }
}