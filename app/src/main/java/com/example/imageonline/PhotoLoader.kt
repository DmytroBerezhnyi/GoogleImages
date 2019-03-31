package com.example.imageonline

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream

class PhotoLoader(val name : String, val imageView : ImageView) : Target {

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        val file = File(Environment.getExternalStorageDirectory().path + "/Pictures/ImageOnline/${name}.jpg")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        file.createNewFile()
        val fileOutputStream = FileOutputStream(file)
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 75,  fileOutputStream)
        fileOutputStream.close()
    }

    override fun onBitmapFailed(errorDrawable: Drawable?) {

    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

    }
}