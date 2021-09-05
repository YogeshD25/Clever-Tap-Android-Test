package com.CleverTapTest.clevertapandroid

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.CleverTapTest.clevertapandroid.databinding.ActivityMainBinding
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.pushnotification.amp.CTPushAmpListener


class MainActivity : AppCompatActivity(), CTPushAmpListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val clevertap = CleverTapAPI.getDefaultInstance(applicationContext)
        val profileUpdate = HashMap<String, Any>()
        val prodViewedAction = HashMap<String, Any>()


        /**
         * Added product view event
         */
        findViewById<Button>(R.id.button).setOnClickListener {
            println("HIT ME")
            prodViewedAction["Product ID"] = 1
            prodViewedAction["Product Image"] =
                "https://d35fo82fjcw0y8.cloudfront.net/2018/07/26020307/customer-success-clevertap.jpg"
            prodViewedAction["Product Name"] = "CleverTap"
            clevertap?.pushEvent("Product viewed", prodViewedAction)
        }

        /**
         * Push Name and Email to CleverTap
         */
        findViewById<Button>(R.id.submit).setOnClickListener {
            println("HIT ME")
            profileUpdate["Name"] = binding.name.text.toString()
            profileUpdate["Email"] = binding.email.text.toString()

            clevertap?.pushProfile(profileUpdate)

            val b = Bundle()
            b.putString("Message", "test")

            val extras = Bundle()
            extras.putString(
                "Message",
                "Username :" + binding.name.text.toString() + ", Email :" + binding.email.text.toString()
            )
            extras.putString("Title", "CleverTap")
            extras.putString("Subject", "UserData")
            CleverTapAPI.processPushNotification(applicationContext, extras)
        }


    }

    /**
     *
     * Generate Notification on receiving payload
     *
     */
    override fun onPushAmpPayloadReceived(extras: Bundle?) {
        val message = extras!!.getString("Message")
        val title = extras!!.getString("Title")
        val subject = extras!!.getString("Subject")

        println("Recieved")
        val notif = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notify: Notification =
            Notification.Builder(applicationContext).setContentTitle(title).setContentText(message)
                .setContentTitle(subject).setSmallIcon(android.R.drawable.ic_dialog_alert).build()

        notify.flags = notify.flags or Notification.FLAG_AUTO_CANCEL
        notif.notify(0, notify)
    }

}