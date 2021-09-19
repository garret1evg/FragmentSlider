package com.example.fragmentslider

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2


const val CHANNEL_ID = "notify_001"
const val FRAG_COUNT = "frag_count"
const val CURR_FRAG = "curr_frag"


class MainActivity : FragmentActivity() {
    private var isDeletedLastFragment = false
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ViewPagerAdapter(this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = adapter

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChanelIfNeeded()

        adapter.fragmentsCount = intent.getIntExtra(FRAG_COUNT, 1)
        viewPager.currentItem = intent.getIntExtra(CURR_FRAG, 1)
        adapter.notifyDataSetChanged()
        val callback = object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == ViewPager2.SCROLL_STATE_IDLE && isDeletedLastFragment){
                    adapter.notifyDataSetChanged()
                    isDeletedLastFragment = false
                }
            }
        }
        viewPager.registerOnPageChangeCallback(callback)
    }
    fun addNewFragment(){
        adapter.fragmentsCount++
        adapter.notifyDataSetChanged()
        viewPager.currentItem = adapter.fragmentsCount
    }
    fun deleteFragment(num: Int){
        viewPager.currentItem = num - 2
        for (i in num - 1 .. adapter.fragmentsCount)
            notificationManager.cancel(i)
        adapter.fragmentsCount = num - 1
        isDeletedLastFragment = true
    }

    fun createNotifications(num: Int){

        val resultIntent = Intent(this, MainActivity::class.java)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        resultIntent.putExtra(FRAG_COUNT, adapter.fragmentsCount)
        resultIntent.putExtra(CURR_FRAG, viewPager.currentItem)
        val resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)


        val icon = BitmapUtils().drawableToBitmap(getDrawable(R.drawable.ic_avatar)!!)
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_notify)
            .setLargeIcon(icon)
            .setContentTitle(getString(R.string.notification_head))
            .setContentText(getString(R.string.notification_text, num))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(resultPendingIntent)

        val notification: Notification = builder.build()

        notificationManager.notify(num, notification)
    }

    private fun createNotificationChanelIfNeeded(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChanel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChanel)
        }
    }



}
