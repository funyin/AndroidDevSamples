package com.initbase.aadprep

import org.hamcrest.core.AnyOf
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    companion object{
        val list:ArrayList<String> = ArrayList()
    }



    @BeforeClass
    fun setUpClass(){

    }

    @Before
    fun clearList(){
        list.clear()
        list.add("4")
    }



    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun simple_unit_test(){
        assertSame("4",list.last())
    }
}