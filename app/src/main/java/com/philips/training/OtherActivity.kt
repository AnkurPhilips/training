package com.philips.training

import android.Manifest.permission.CAMERA
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_other.*
import kotlinx.android.synthetic.main.activity_other.button
import kotlinx.android.synthetic.main.activity_other.et
import kotlinx.android.synthetic.main.activity_other.tv
import java.util.*

private const val REQUEST_IMAGE_CAPTURE = 101
private const val REQUEST_IMAGE_CAPTURE_PERMISSION = 102
private const val REQUEST_RESULT_ACTIVITY: Int = 103

class OtherActivity : AppCompatActivity() , ActivityCompat.OnRequestPermissionsResultCallback {
    private var name = ""
    private var password = ""

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode== REQUEST_IMAGE_CAPTURE_PERMISSION){
            camera.performClick()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)
        intent?.let {
            name = it.getStringExtra(NAME) ?: ""
            password = it.getStringExtra(PASSWORD) ?: ""
        }
        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$name"))
            (this@OtherActivity).startActivity(intent)
        }

        alarm.setOnClickListener{
            val intent = Intent(AlarmClock.ACTION_SET_ALARM)
            val time = Date()
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, "ALARM FROM APP");
            intent.putExtra(AlarmClock.EXTRA_HOUR, time.hours);
            intent.putExtra(AlarmClock.EXTRA_MINUTES, time.minutes + 1)
            startActivity(intent)
        }

        camera.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                val permission = listOf(CAMERA)
                if (ContextCompat.checkSelfPermission(this, CAMERA)
                    == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, permission.toTypedArray(), REQUEST_IMAGE_CAPTURE_PERMISSION)
                }else{
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }

            } catch (e: ActivityNotFoundException) {
                AlertDialog.Builder(this).setTitle(getString(R.string.camera)).setMessage(getString(R.string.camera_not_found)).show()
            }
        }

        resultActivity.setOnClickListener{
            val intent = Intent(this, ResultActivity::class.java)
            startActivityForResult(intent, REQUEST_RESULT_ACTIVITY)
        }

    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){
                AlertDialog.Builder(this).setTitle(getString(R.string.camera)).setMessage(getString(R.string.image_captured)).setPositiveButton(getString(R.string.ok), null).show()
            }
        } else if (requestCode == REQUEST_RESULT_ACTIVITY){
            if(resultCode == RESULT_OK){
                AlertDialog.Builder(this).setTitle(getString(R.string.result)).setMessage(getString(R.string.result_received)).setPositiveButton(getString(R.string.ok), null).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        tv.setText(name)
        et.setText(password)
    }
}