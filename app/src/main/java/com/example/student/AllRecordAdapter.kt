package com.example.student

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AllRecordsAdapter(private val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mySQLiteOpenHelper = MySQLiteOpenHelper(mContext)
    private var students:List<Student> = mySQLiteOpenHelper.read()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_student_layout2, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as MyViewHolder
        myHolder.rollNumberTextView.text = students[position].rollNumber
        myHolder.nameTextView.text = students[position].name
        myHolder.cgpaTextView.text = students[position].cgpa.toString()
        myHolder.genderTextView.text = if (students[position].gender) "Male" else "Female"
        myHolder.degreeTextView.text = (students[position].degree)
        myHolder.dobTextView.text = mySQLiteOpenHelper.calendarToString(students[position].dob)
        myHolder.careerInterestTextView.text =(
                (if (students[position].ciAcademia) "Academia" else " " ) + "," +
                        (if (students[position].ciIndustry) "Industry" else " " ) + "," +
                        (if (students[position].ciBusiness) "Business" else " " ) + "").toString()

        myHolder.editButton.setOnClickListener {

        }
        myHolder.deleteButton.setOnClickListener {
            mySQLiteOpenHelper.delete(students[position].rollNumber)
            Toast.makeText(mContext, students[position].rollNumber+" data is deleted!", Toast.LENGTH_LONG).show()
            students =mySQLiteOpenHelper.read()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mySQLiteOpenHelper.getRecordCount().toInt()
    }

    class MyViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val rollNumberTextView: TextView = itemView.findViewById(R.id.tv_roll_number)
        val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        val cgpaTextView: TextView = itemView.findViewById(R.id.tv_cgpa)
        val genderTextView: TextView = itemView.findViewById(R.id.tv_gender)
        val degreeTextView: TextView = itemView.findViewById(R.id.tv_degree)
        val dobTextView: TextView = itemView.findViewById(R.id.tv_dob)
        val careerInterestTextView: TextView = itemView.findViewById(R.id.tv_interest)
        val editButton: Button = itemView.findViewById(R.id.btn_edit_record)
        val deleteButton: Button = itemView.findViewById(R.id.btn_delete_record)
    }

}
