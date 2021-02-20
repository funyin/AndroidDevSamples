package com.initbase.aadprep.sqlite

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.initbase.aadprep.R
import java.text.SimpleDateFormat
import java.util.*

class AddEmployeeActivity : AppCompatActivity() {

    val fullName by lazy { findViewById<TextInputLayout>(R.id.edt_full_name) }
    val dateOfBirth by lazy { findViewById<TextInputLayout>(R.id.edt_date_of_birth) }
    val designation by lazy { findViewById<TextInputLayout>(R.id.edt_designation) }
    val calendar by lazy { Calendar.getInstance() }
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)
        databaseHelper = DatabaseHelper(this)

        if (intent.extras != null) {
            dateOfBirth.editText?.setText(intent.getStringExtra("dob"))
            fullName.editText?.setText(intent.getStringExtra("fullName"))
            designation.editText?.setText(intent.getStringExtra("designation"))

            findViewById<Button>(R.id.btn_save).text = "Update"
        }

        dateOfBirth.editText?.setOnClickListener {
            setUpCalendar()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.globo_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var dialog = AlertDialog.Builder(this)
        dialog.apply {
            title = "Delete Record"
            setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    DataManager().deleteEmployee(DatabaseHelper(this@AddEmployeeActivity),intent.getStringExtra("id"))
                    dialog?.dismiss()
                    finish()
                }
            })
            setNegativeButton("No", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }
            })
            setMessage("Are you sure you want to delete this employee")
            create().show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun setUpCalendar() {
        DatePickerDialog(
            this,
            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    dateOfBirth.editText?.setText(getFormattedDate(calendar.timeInMillis))
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        ).show()
    }

    fun getFormattedDate(date: Long): String = date.let {
        SimpleDateFormat("d MMM, yyyy", Locale.getDefault()).format(date)
    } ?: "Not Found"

    fun cancelClicked(view: View) {
        finish()
    }

    fun saveEmployee(view: View) {
        var isValid = true
        dateOfBirth?.editText?.error = if (dateOfBirth.editText?.text?.isEmpty()!!) {
            isValid = false
            "Date of birth is required"
        } else {
            isValid = true
            null
        }

        fullName?.editText?.error = if (fullName.editText?.text?.isEmpty()!!) {
            isValid = false
            "Full name required"
        } else {
            isValid = true
            null
        }

        designation?.editText?.error = if (designation.editText?.text?.isEmpty()!!) {
            isValid = false
            "Employee designation is required"
        } else {
            isValid = true
            null
        }

        if (isValid) {
            val fullName = fullName.editText?.text.toString()
            val dateOfBirth = calendar.timeInMillis
            val designation = designation.editText?.text.toString()

            val db = databaseHelper.writableDatabase
            val values = ContentValues()
            values.put(GloboMedContract.EmployeeEntry.COLUMN_NAME, fullName)
            values.put(GloboMedContract.EmployeeEntry.COLUMN_DESIGNATION, designation)
            values.put(GloboMedContract.EmployeeEntry.COLUMN_DOB, dateOfBirth)
            val result: Long

            if (intent.extras == null) {
                result = db.insert(GloboMedContract.EmployeeEntry.TABLE_NAME, null, values)
                Toast.makeText(this, "Employee Successfully added: $result", Toast.LENGTH_LONG)
                    .show()
            } else {
                result = db.update(
                    GloboMedContract.EmployeeEntry.TABLE_NAME,
                    values,
                    GloboMedContract.EmployeeEntry.COLUMN_ID + " Like ?",
                    arrayOf(intent.getStringExtra("id")),
                ).toLong()
                Toast.makeText(this, "Employee Successfully updated: $result", Toast.LENGTH_LONG)
                    .show()
            }
            setResult(RESULT_OK, Intent())
            finish()
        }

    }
}