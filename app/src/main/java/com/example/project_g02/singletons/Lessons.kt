package com.example.project_g02.singletons

import com.example.project_g02.models.Lesson

object Lessons {
    val lessonList = listOf(
        Lesson(
            "Get Started With HTML and CSS",
            calcSecs(8,43),
            "Learning what HTML and CSS are used for.",
            "https://www.youtube.com/watch?v=pm5OVxpul48"
        ),
        Lesson(
            "Let's Create Our First HTML Project",
            calcSecs(10,59),
            "Creating our first HTML project and document.",
            "https://www.youtube.com/watch?v=bupWPZdXqIA"
        ),
        Lesson(
            "Learn About HTML Elements and Attributes",
            calcSecs(5,38),
            "Learning about different HTML elements and attributes.",
            "https://www.youtube.com/watch?v=XXrpsQqAlIQ"
        ),
        Lesson(
            "Create Titles and Text Using HTML",
            calcSecs(7,11),
            "Learning how to create titles and text.",
            "https://www.youtube.com/watch?v=8bZ4RPjOPYE"
        ),
        Lesson(
            "We Use Boxes in Websites",
            calcSecs(7,3),
            "Learning to use boxes in websites.",
            "https://www.youtube.com/watch?v=1YO_5jXC-bo"
        )
    )

    private fun calcSecs(minutes: Int, seconds: Int):Int {
        return ((minutes*60)+seconds)
    }
}