package com.example.imageonline

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view.*

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
        var res = 0;
        val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissions.forEach {
            res = checkCallingOrSelfPermission(it)
            if (res != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    private fun requestPerms() {
        val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }
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
        Snackbar.make(imageView, "Storage permission isn't granted", Snackbar.LENGTH_LONG)
            .setAction("Settings") {
                openApplicationSettings()
                Toast.makeText(this, "Open Permissions and grant the storage permission", Toast.LENGTH_SHORT).show()}
    }

    private fun openApplicationSettings() {
        val appSettingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS); Uri.parse("package: ${packageName}")
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == PERMISSION_REQUEST_CODE) {
            savePicture(src, imageView)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            val message = "Storage permission is neded to save pictures"
            Snackbar.make(imageView, message, Snackbar.LENGTH_LONG)
                .setAction("GRANT") {
                    requestPerms()
                }.show()
        } else {
            requestPerms()
        }
    }

    private fun savePicture(src: String, imageView: ImageView?) {
        Toast.makeText(this, "The picture has been saved to ...", Toast.LENGTH_LONG).show()
        Picasso.with(this)
            .load(src)
            .into(PhotoLoader(System.currentTimeMillis().toString(), imageView!!))
    }
}
