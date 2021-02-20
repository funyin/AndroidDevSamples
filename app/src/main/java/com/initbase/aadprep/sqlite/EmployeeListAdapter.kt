package com.initbase.aadprep.sqlite

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.initbase.aadprep.R
import com.initbase.aadprep.sqlite.EmployeeListAdapter.EmployeeListHolder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EmployeeListAdapter(val context: Context,val dbHelper: DatabaseHelper,var employees:ArrayList<Employee>) : RecyclerView.Adapter<EmployeeListHolder>() {

    inner class EmployeeListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeListHolder {
        return EmployeeListHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list_employee, parent, false)
        )
    }

    fun dataSetChanged(employees: ArrayList<Employee>) {
        this.employees = employees
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: EmployeeListHolder, position: Int) {
        val currentEmployee = employees[position]
        holder.itemView.findViewById<TextView>(R.id.employee_name).text = currentEmployee.name
        holder.itemView.findViewById<TextView>(R.id.employee_designation).text = currentEmployee.designation

        holder.itemView.setOnClickListener {
            val employee  = DataManager().fetchEmployee(dbHelper,position+1)
            Log.d(this.javaClass.simpleName,employee.toString())
            (context as GloboMedActivity).startActivityForResult(Intent(context,AddEmployeeActivity::class.java).apply {
                putExtra("dob",SimpleDateFormat("d MMM yy").format(Date(employee?.dob!!)))
                putExtra("fullName",employee.name)
                putExtra("id",employee.id)
                putExtra("designation",employee.designation)
            },1)
        }
    }

    override fun getItemCount(): Int = employees.size
}