package com.example.project_g02.singletons
import com.example.project_g02.models.Lesson
//Singleton object used to store a global list of all the lessons and keep it identical across all
//the parts of the application. Likely a cleaner implementation than setting up all the Lesson objects
//in the MainActivity onCreate in a list and serializing them to pass around the different activities.

object Lessons {
    val lessonList = listOf(
        Lesson(
            "Get Started With HTML and CSS",
            calcSecs(0,8,43),
            "Learning what HTML and CSS are used for.",
            "https://www.youtube.com/watch?v=pm5OVxpul48"
        ),
        Lesson(
            "Let's Create Our First HTML Project",
            calcSecs(0,10,59),
            "Creating our first HTML project and document.",
            "https://www.youtube.com/watch?v=bupWPZdXqIA"
        ),
        Lesson(
            "Learn About HTML Elements and Attributes",
            calcSecs(0,5,38),
            "Learning about different HTML elements and attributes.",
            "https://www.youtube.com/watch?v=XXrpsQqAlIQ"
        ),
        Lesson(
            "Create Titles and Text Using HTML",
            calcSecs(0,7,11),
            "Learning how to create titles and text.",
            "https://www.youtube.com/watch?v=8bZ4RPjOPYE"
        ),
        Lesson(
            "We Use Boxes in Websites",
            calcSecs(0,7,3),
            "Learning to use boxes in websites.",
            "https://www.youtube.com/watch?v=1YO_5jXC-bo"
        )
    )

    //Basic helper function to convert HH:MM:SS timestamp to an Int of the video's duration in seconds.
    //None of the Lessons here have any hour timestamps but it's included in the function for future use.
    private fun calcSecs(hours: Int, minutes: Int=0, seconds: Int=0):Int {
        return ((hours*3600)+(minutes*60)+seconds)
    }
}