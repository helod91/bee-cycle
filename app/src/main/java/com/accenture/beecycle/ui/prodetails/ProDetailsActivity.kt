package com.accenture.beecycle.ui.prodetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.accenture.beecycle.databinding.ActivityProDetailsBinding
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class ProDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(ActivityProDetailsBinding.inflate(LayoutInflater.from(this))) {
            proDetailsClose.setOnClickListener {
                onBackPressed()
            }

            setContentView(root)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}