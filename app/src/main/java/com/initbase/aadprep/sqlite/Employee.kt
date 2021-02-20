package com.initbase.aadprep.sqlite

class Employee (
    val id:String,
    val name:String,
    val dob:Long,
    val designation:String,


) {
    override fun toString(): String {
        return ("Employee $id $name $dob $designation")
    }
}