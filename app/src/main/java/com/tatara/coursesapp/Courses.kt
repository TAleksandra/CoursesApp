package com.tatara.coursesapp

import androidx.annotation.DrawableRes

data class Courses(
    val rating: Float,
    val title: String,
    @DrawableRes val thumbnail: Int,
    val body: String
)

val course1 = Courses(
    4.6f,
    "SQL - MySQL for Data Analytics and Business Intelligence",
    R.drawable.course1,
    "SQL that will get you hired – SQL for Business Analysis, Marketing, and Data Management"
)

val course2 = Courses(
    4.6f,
    "The Project Management Course: Beginner to PROject Manager",
    R.drawable.course2,
    "The Complete Course For Becoming A Successful Project Manager"
)

val course3 = Courses(
    4.5f,
    "Security Awareness Training, Internet Security for Employees",
    R.drawable.course3,
    "Basic security awareness guide on Internet security and privacy to help keep you, your home, and your employer safe."
)

val course4 = Courses(
    4.4f,
    "Mastering Collaboration: Work together for the best results",
    R.drawable.course4,
    "Learn how to assemble, lead, & organize collaborative teams & create a thriving culture of business collaboration"
)

val course5 = Courses(
    4.5f,
    "Scrum Master Certification 2024 + Agile Scrum Certification",
    R.drawable.course5,
    "Overview of Scrum Agile project management+common questions+tips to pass PSM scrum org ONLINE Scrum Master Certification"
)

val course6 = Courses(
    4.6f,
    "The Business Intelligence Analyst Course 2024",
    R.drawable.course6,
    "The skills you need to become a BI Analyst - Statistics, Database theory, SQL, Tableau – Everything is included"
)

val course7 = Courses(
    4.4f,
    "Design Thinking for Beginners: Develop Innovative Ideas",
    R.drawable.course7,
    "Apply the five-step design thinking process to identify and creatively solve problems using a human-centered approach."
)

val course8 = Courses(
    4.4f,
    "Smart Tips: Communication",
    R.drawable.course8,
    "A collection of 61 micro-lessons on effective communication, presentation skills, feedback, and complex conversations"
)

val allCourses = listOf(course1, course2, course3, course4, course5, course6, course7, course8)