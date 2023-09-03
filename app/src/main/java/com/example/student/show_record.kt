package com.example.student

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class show_record : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_record)
        val recyclerview = findViewById<RecyclerView>(R.id.all_record_recycler_view)

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = AllRecordsAdapter(this)

        findViewById<Button>(R.id.back_to_main).setOnClickListener {
            finish()
        }
    }
}