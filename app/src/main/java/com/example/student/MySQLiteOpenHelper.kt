package com.example.student

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import java.util.Date



class MySQLiteOpenHelper constructor(context: Context?): SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(db: SQLiteDatabase?)
    {
        val createStudentTable=("CREATE TABLE "+ TABLE_STUDENTS+
                "(" + COLUMN_ROLL_NUMBER + " TEXT PRIMARY KEY,"+
                COLUMN_NAME + " TEXT, "+
                COLUMN_CGPA + " NUMERIC, " +
                COLUMN_DEGREE + " TEXT, " +
                COLUMN_GENDER + " NUMERIC, " +
                COLUMN_DATE_OF_BIRTH + " TEXT, " +
                COLUMN_CAREER_INTEREST_ACADEMIA + " NUMERIC, " +
                COLUMN_CAREER_INTEREST_INDUSTRY + " NUMERIC, " +
                COLUMN_CAREER_INTEREST_BUSINESS + " NUMERIC "+")"  )
        Log.d("DB_CREATED", "Database created successfully")
        db?.execSQL(createStudentTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        TODO("Not yet implemented")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENTS")
        onCreate(db)
    }
    //------------CREATE STUDENT RECORD ------------------------//
    fun create(student:Student){
        val values = ContentValues()
        values.put(COLUMN_ROLL_NUMBER,student.rollNumber)
        values.put(COLUMN_NAME,student.name)
        values.put(COLUMN_CGPA,student.cgpa)
        values.put(COLUMN_DEGREE,student.degree)
        values.put(COLUMN_GENDER,student.gender)
        values.put(COLUMN_DATE_OF_BIRTH,(student.dob).toString())
        values.put(COLUMN_CAREER_INTEREST_ACADEMIA,student.ciAcademia)
        values.put(COLUMN_CAREER_INTEREST_INDUSTRY,student.ciBusiness)
        values.put(COLUMN_CAREER_INTEREST_BUSINESS,student.ciBusiness)
        val db = this.writableDatabase
        Log.d("DBWRITE",values.toString())
        Log.d("DB_INSERT", "Data inserted successfully")
        db.insert(TABLE_STUDENTS,null,values)
    }
    //------------UPDATE STUDENT RECORD ------------------------//
    fun update(student:Student){
        val values=ContentValues()
        values.put(COLUMN_ROLL_NUMBER,student.rollNumber)
        values.put(COLUMN_NAME,student.name)
        values.put(COLUMN_CGPA,student.cgpa)
        values.put(COLUMN_DEGREE,student.degree)
        values.put(COLUMN_GENDER,student.gender)
        values.put(COLUMN_DATE_OF_BIRTH,student.dob.toString())
        values.put(COLUMN_CAREER_INTEREST_ACADEMIA,student.ciAcademia)
        values.put(COLUMN_CAREER_INTEREST_INDUSTRY,student.ciBusiness)
        values.put(COLUMN_CAREER_INTEREST_BUSINESS,student.ciBusiness)
        val db=this.writableDatabase

        db.update(
            TABLE_STUDENTS,
            values,
            "$COLUMN_ROLL_NUMBER = ?",
            arrayOf(student.rollNumber.toString())
        )
    }
    //------------DELETE STUDENT RECORD ------------------------//
    fun delete(rollNumb:String){
        val db=this.writableDatabase
        db.delete(
            TABLE_STUDENTS,
            "$COLUMN_ROLL_NUMBER=?",
            arrayOf(rollNumb.toString())
        )



    }
    fun read(): ArrayList<Student> {
        val sql = "SELECT * FROM $TABLE_STUDENTS"
        val db = this.readableDatabase
        val storeStudents = ArrayList<Student>()
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val rollNumber = cursor.getString(0).toString()
                val name = cursor.getString(1).toString()
                val cgpa = cursor.getString(2).toDouble()
                val degree = cursor.getString(3).toString()
                val gender = (cursor.getString(4) == "1")
                val dateOfBirth = cursor.getString(5).toString()
                val careerInterestAcademia = (cursor.getString(6) == "1")
                val careerInterestIndustry = (cursor.getString(7) == "1")
                val careerInterestBusiness = (cursor.getString(8) == "1")
                val dob = stringToDate(dateOfBirth)
                if (dob != null) {
                    storeStudents.add(
                        Student(
                            rollNumber,
                            name,
                            cgpa,
                            degree,
                            gender,
                            java.sql.Date(dob.time),
                            careerInterestAcademia,
                            careerInterestIndustry,
                            careerInterestBusiness
                        )
                    )
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return storeStudents
    }


    fun calendarToString(date: java.sql.Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(date)
    }


    fun stringToDate(dateString: String): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val utilDate = formatter.parse(dateString)
        return utilDate?.let { Date(it.time) }
    }





    fun getRecordCount(): Long {
        val db = this.readableDatabase
        val count = DatabaseUtils.queryNumEntries(db, TABLE_STUDENTS)
        db.close()
        return count
    }
    companion object {
        private const val DATABASE_VERSION = 5
        private const val DATABASE_NAME = "CRUD"
        private const val TABLE_STUDENTS = "Students"
        private const val COLUMN_ROLL_NUMBER = "_rollNumber"
        private const val COLUMN_NAME = "studentName"
        private const val COLUMN_CGPA = "cgpa"
        private const val COLUMN_DEGREE = "degree"
        private const val COLUMN_GENDER = "gender"
        private const val COLUMN_DATE_OF_BIRTH = "dateOfBirth"
        private const val COLUMN_CAREER_INTEREST_ACADEMIA = "careerInterestAcademia"
        private const val COLUMN_CAREER_INTEREST_INDUSTRY = "careerInterestIndustry"
        private const val COLUMN_CAREER_INTEREST_BUSINESS = "careerInterestBusiness"

    }
}