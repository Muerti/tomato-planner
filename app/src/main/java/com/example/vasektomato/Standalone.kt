package com.example.vasektomato

import android.os.CountDownTimer
import com.example.vasektomato.adapter.TomatoTask
import java.text.SimpleDateFormat
import java.util.*

object Singleton {
    var timer: CountDownTimer? = null
    var timeleft: Long = 0
    private var isActive: Boolean = false

    var instance: Singleton? = null
        get() {
            if (field == null) {
                synchronized(Singleton::class.java) {
                    if (field == null) {
                        field = Singleton
                    }
                }
            }
            return field
        }
        private set

    fun start(mainTime: Long, onTick: (timeToFinish: Long) -> Unit) {
        if (timer == null) {
            timer = object : CountDownTimer(mainTime * 60000L, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    onTick
                    timeleft = millisUntilFinished
                }

                override fun onFinish() {
                    TODO("Not yet implemented")
                }
            }
            (timer as CountDownTimer).start()
            isActive = true
            return
        }
        if (isActive) return

        timer?.start()
        isActive = true
    }
}