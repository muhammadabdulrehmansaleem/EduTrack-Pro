package com.example.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import java.sql.Date
import android.widget.Button
import android.widget.Toast

class AddRecord : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)
        val spDegrees=findViewById<Spinner>(R.id.sp_degrees)
        val adapter=ArrayAdapter.createFromResource(this,R.array.sp_degrees,android.R.layout.simple_spinner_item)
        spDegrees.adapter=adapter

        var dataBase = MySQLiteOpenHelper(this)

        findViewById<Button>(R.id.btn_save_record).setOnClickListener{
            val student=Student(
                findViewById<EditText>(R.id.et_roll_number).text.toString(),
                findViewById<EditText>(R.id.et_name).text.toString(),
                findViewById<EditText>(R.id.et_cgpa).text.toString().toDouble(),
                findViewById<Spinner>(R.id.sp_degrees).selectedItem.toString(),
                findViewById<RadioButton>(R.id.rb_male).isChecked,
                Date(findViewById<DatePicker>(R.id.dp_dob).year,
                    findViewById<DatePicker>(R.id.dp_dob).month,
                    findViewById<DatePicker>(R.id.dp_dob).dayOfMonth),
                findViewById<CheckBox>(R.id.cb_academia).isChecked,
                findViewById<CheckBox>(R.id.cb_industry).isChecked,
                findViewById<CheckBox>(R.id.cb_business).isChecked)

            dataBase.create(student)
            finish()

            Toast.makeText(this,student.toString(),Toast.LENGTH_LONG).show()
            Log.d("INFO",student.toString())
        }


    }
    override fun onDestroy() {
        super.onDestroy()
        var dataBase = null
       // dataBase.close()
    }
}