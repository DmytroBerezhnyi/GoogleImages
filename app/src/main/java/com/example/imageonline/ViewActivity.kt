package com.example.imageonline

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view.*
import java.util.jar.Manifest

class ViewActivity : AppCompatActivity() {

    private lateinit var src : String
    private val PERMISSION_REQUEST_CODE = 101;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        src = intent.extras.getString("src")
        Picasso.with(this)
            .load(src)
            .into(imageView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId) {
            R.id.menu_save_picture -> {
                if (hasPermission()) {
                    savePicture(src, imageView)
                } else {
                    requestPermissionWithRationale()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun hasPermission(): Boolean {
        val res = 0;
        val permissions = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        return res == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        var allowed = true
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> grantResults.forEach { allowed = allowed && (it == PackageManager.PERMISSION_GRANTED) }
            else -> allowed = false
        }
        if (allowed) {
            savePicture(src, imageView)
        } else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Storage permissions denied", Toast.LENGTH_LONG).show()
                } else {
                    showNoStoragePermissionSnackBar()
                }
            }
        }
    }

    private fun showNoStoragePermissionSnackBar() {

    }

    private fun savePicture(src: String, imageView: ImageView?) {
        Toast.makeText(this, "The picture has been saved to ...", Toast.LENGTH_LONG).show()
        Picasso.with(this)
            .load(src)
            .into(PhotoLoader(System.currentTimeMillis().toString(), imageView!!))
    }
}
