package com.initbase.aadprep.sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.initbase.aadprep.R


class GloboMedActivity : AppCompatActivity() {
    val ADD_EMPLOYEE_RESLT_CODE = 1
    private val employees: ArrayList<Employee>
        get() {
            return DataManager().fetchEmployees(DatabaseHelper(this))
        }
    val rvEmployees by lazy { findViewById<RecyclerView>(R.id.rv_employees) }
    val dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_globo_med)


        rvEmployees.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvEmployees.adapter = EmployeeListAdapter(this,dbHelper, employees)
    }

    fun addEmployee(view: View) {
        startActivityForResult(
            Intent(this, AddEmployeeActivity::class.java),
            ADD_EMPLOYEE_RESLT_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_EMPLOYEE_RESLT_CODE)
            (rvEmployees.adapter as EmployeeListAdapter).dataSetChanged(employees)
    }
}