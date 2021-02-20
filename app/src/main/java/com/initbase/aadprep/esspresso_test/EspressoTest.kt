package com.initbase.aadprep.esspresso_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.initbase.aadprep.R

class EspressoTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_espresso_test)

        val editText: EditText = findViewById(R.id.edit_text)
        findViewById<Button>(R.id.btn_submit).setOnClickListener {
            Snackbar.make(
                editText,
                editText.text ?: "You have to input the test description",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}