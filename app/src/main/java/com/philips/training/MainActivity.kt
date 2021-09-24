package com.philips.training

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val NAME="name"
const val PASSWORD="password"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        button.setOnClickListener {

            val name = tv.text.toString()
            val password = et.text.toString()
            if(name.isNotBlank() && password.isNotBlank()) {
                val intent = Intent(this, OtherActivity::class.java)
                intent.putExtra(NAME, name)
                intent.putExtra(PASSWORD, password)
                startActivity(intent)
                finish()
            }
        }
    }
}