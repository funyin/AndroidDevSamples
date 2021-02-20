package com.initbase.aadprep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.initbase.aadprep.activity_for_result.ActivityForResult
import com.initbase.aadprep.esspresso_test.EspressoTest
import com.initbase.aadprep.settings.SettingsActivity
import com.initbase.aadprep.sqlite.GloboMedActivity

class MainActivity : AppCompatActivity() {
    var apps = ArrayList<String>()

    companion object {
        val testItem = "Testing and Automated UI Tests"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apps.add("Start Activity for Result")
        apps.add(testItem)
        apps.add("Settings and preference screen")
        apps.add("SQLite Fundamentals")

        val listView = findViewById<ListView>(R.id.list_view)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, apps)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> startActivity(Intent(this, ActivityForResult::class.java))
                1 -> startActivity(Intent(this, EspressoTest::class.java))
                2 -> startActivity(Intent(this, SettingsActivity::class.java))
                3 -> startActivity(Intent(this,GloboMedActivity::class.java))
            }
        }
    }
}