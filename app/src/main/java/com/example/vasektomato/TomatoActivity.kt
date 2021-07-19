package com.example.vasektomato

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vasektomato.adapter.ExpandableListTomatoAdapter
import com.example.vasektomato.adapter.TomatoTask
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class TomatoActivity : AppCompatActivity() {

    private var expandableListView: ExpandableListView? = null

    private  var adapter: ExpandableListTomatoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tomato)
        val name = intent.getStringExtra("name")
        val tomatoesAmount = intent.getIntExtra("tomatoesAmount", 0)
        val mainTime = intent.getIntExtra("mainTime", 0)
        val pauseTime = intent.getIntExtra("pauseTime", 0)
        val leftTime = intent.getIntExtra("leftTime", 0)

        title = name!!
        createExpandableListView(tomatoesAmount, mainTime, pauseTime)
        val startButton = findViewById<Button>(R.id.startTomatoButton)
        var currentTomato = leftTime/ (pauseTime+mainTime)
        // посчитать остаток времени таймера от конкретного томата ( от паузы или основного времени)

        startButton.setOnClickListener {
            startTimer(tomatoesAmount, mainTime)
            (it as Button).text = "Stop"
            (it as Button).setOnClickListener() ;

        }

    }

    private fun startTimer(tomatoesAmount: Int, mainTime: Int) {
        //todo:get remain time from main activity, save remain time in class field
        // calc which tomatotask is now active from remain time
        // and then start main or pause time of needed child
        // on destroying save left time
        Singleton.instance?.start(mainTime * 60000L, )

    }
     fun onTick(millisUntilFinished: Long) {
        val child: TomatoTask = adapter?.getChild(i - 1, 0) as TomatoTask
        val dateFormat = SimpleDateFormat("mm:ss")
        child?.time_remain = dateFormat.format(Date(millisUntilFinished)).toString()
        adapter?.changeChild(i - 1, 0, child)
        adapter?.notifyDataSetChanged()
    }

    private fun createExpandableListView(
        tomatoesAmount: Int,
        mainTime: Int,
        pauseTime: Int
    ) {
        expandableListView = findViewById(R.id.expandableListViewTomato)
        if (expandableListView != null) {
            val titleList = mutableListOf<String>()
            val listData = HashMap<String, MutableList<TomatoTask>>()

            for (i in 1..tomatoesAmount) {
                val name = "".plus(i).plus(".").plus(" томат")
                titleList.add(name)
                listData[name] =
                    mutableListOf(TomatoTask(mainTime, false), TomatoTask(pauseTime, false))
            }
            adapter = ExpandableListTomatoAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
            expandableListView!!.setOnGroupExpandListener { groupPosition ->
                //todo: expandable items?
            }
            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                Toast.makeText(
                    applicationContext,
                    "Clicked: " + (titleList)[groupPosition] + " -> " + listData[(
                            titleList
                            )
                            [groupPosition]]!![childPosition],
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
        }
    }

}
