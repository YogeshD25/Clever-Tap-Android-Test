package com.CleverTapTest.clevertapandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.CleverTapTest.clevertapandroid.databinding.ActivityMainBinding
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        ActivityLifecycleCallback.register(application);
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val clevertap = CleverTapAPI.getDefaultInstance(applicationContext)
        val profileUpdate = HashMap<String, Any>()
        val prodViewedAction = HashMap<String, Any>()

        findViewById<Button>(R.id.button).setOnClickListener {
            println("HIT ME")
            prodViewedAction["Product ID"] = 1
            prodViewedAction["Product Image"] = "https://d35fo82fjcw0y8.cloudfront.net/2018/07/26020307/customer-success-clevertap.jpg"
            prodViewedAction["Product Name"] = "CleverTap"
            clevertap?.pushEvent("Product viewed", prodViewedAction)
        }




        findViewById<Button>(R.id.submit).setOnClickListener {
            println("HIT ME")
            profileUpdate["Name"] = binding.name.text.toString()
            profileUpdate["Email"] = binding.email.text.toString()

            clevertap?.pushProfile(profileUpdate);
        }




    }

}