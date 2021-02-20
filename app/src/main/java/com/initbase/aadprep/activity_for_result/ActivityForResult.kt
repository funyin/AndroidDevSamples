package com.initbase.aadprep.activity_for_result

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import com.initbase.aadprep.R

class ActivityForResult : AppCompatActivity() {
    private val TAKE_PICTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_result)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val btn: Button = findViewById(R.id.btn_startActivity)
        btn.setOnClickListener {
            startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), TAKE_PICTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TAKE_PICTURE) {
            if (resultCode == RESULT_OK)
                findViewById<ImageView>(R.id.image_view).setImageBitmap(data?.getParcelableExtra<Bitmap>("data"))
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}