package com.initbase.aadprep.sqlite

import com.initbase.aadprep.sqlite.GloboMedContract.EmployeeEntry

class DataManager {

    fun fetchEmployees(db: DatabaseHelper): ArrayList<Employee> {
        val employees: ArrayList<Employee> = ArrayList()
        val db = db.readableDatabase
        val columns = arrayOf(
            EmployeeEntry.COLUMN_ID,
            EmployeeEntry.COLUMN_NAME,
            EmployeeEntry.COLUMN_DESIGNATION,
            EmployeeEntry.COLUMN_DOB,
        )

        val cursor = db.query(
            EmployeeEntry.TABLE_NAME, columns, null, null, null, null, null
        )

        while (cursor.moveToNext()) {
            employees.add(
                Employee(
                    cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_NAME)),
                    cursor.getLong(cursor.getColumnIndex(EmployeeEntry.COLUMN_DOB)),
                    cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_DESIGNATION)),
                )
            )
        }
        cursor.close()
        return employees
    }

    fun fetchEmployee(dbHelper: DatabaseHelper, id: Int): Employee? {
        val db = dbHelper.readableDatabase
        var employee: Employee? = null

        val projection = arrayOf(
            EmployeeEntry.COLUMN_NAME,
            EmployeeEntry.COLUMN_DOB,
            EmployeeEntry.COLUMN_DESIGNATION
        )
        val selection = EmployeeEntry.COLUMN_ID + " LIKE ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(
            EmployeeEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            employee =
                Employee(
                    id.toString(),
                    cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_NAME)),
                    cursor.getLong(cursor.getColumnIndex(EmployeeEntry.COLUMN_DOB)),
                    cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_DESIGNATION)),
                )
        }
        cursor.close()
        return employee
    }

    fun deleteEmployee(dbHelper: DatabaseHelper, id: String?): Int {
        val db = dbHelper.writableDatabase
        return db.delete(EmployeeEntry.TABLE_NAME, EmployeeEntry.COLUMN_ID + " LIKE ?",
        arrayOf(id),)
    }
}