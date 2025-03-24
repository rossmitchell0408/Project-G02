package com.example.project_g02.models

//Using a BooleanArray over Array<Boolean> to avoid a minimal amount of overhead.
//Using a BooleanArray also lets us short cut the initialization by using BooleanArray(size) which
//returns a BooleanArray of the specified size with all the entries equal to false.
data class User(val name: String, var completed: BooleanArray)
