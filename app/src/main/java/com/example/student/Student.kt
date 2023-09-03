package com.example.student
import java.sql.Date
data class Student(val rollNumber:String,
                   val name:String,
                   val cgpa:Double,
                   val degree:String,
                   val gender:Boolean,
                   val dob:Date,
                   val ciAcademia:Boolean,
                   val ciIndustry:Boolean,
                   val ciBusiness:Boolean
)

