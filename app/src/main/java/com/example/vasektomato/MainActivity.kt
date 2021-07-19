package com.example.vasektomato

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vasektomato.adapter.TomatoesListAdapter
import com.example.vasektomato.databinding.ActivityMainBinding
import com.example.vasektomato.db.model.Tomato
import com.example.vasektomato.viewmodel.TomatoViewModel
import com.example.vasektomato.viewmodel.TomatoViewModelFactory

class MainActivity : AppCompatActivity(), TomatoesListAdapter.OnItemClickListener{

    private lateinit var binding: ActivityMainBinding
    private val newTomatoSettingsActivityRequestCode = 1
    private val newTomatoActivityRequestCode = 2

    private lateinit var tomatoAdapter: TomatoesListAdapter
    private val tomatoViewModel: TomatoViewModel by viewModels {
        TomatoViewModelFactory((application as TomatoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
                val intent = Intent(this, TomatoSettingsActivity::class.java)
                startActivityForResult(intent,newTomatoSettingsActivityRequestCode)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        tomatoAdapter = TomatoesListAdapter(this)
        recyclerView.adapter = tomatoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        tomatoViewModel.allTomatoes.observe(this, { tomatoes ->
            // Update the cached copy of the words in the adapter.
            tomatoes?.let {
                tomatoAdapter.submitList(it)
            }
        })



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        if (requestCode == newTomatoSettingsActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val tomatoesAmount = data.getIntExtra("tomatoesAmount", 0)
            val mainTime = data.getIntExtra("mainTime", 0)
            val pauseTime = data.getIntExtra("pauseTime", 0)
            val name : String? = data.getStringExtra("name")
            name?.let {
                tomatoViewModel.insert(Tomato(it, pauseTime, mainTime, tomatoesAmount))
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(position: Int) {
        val tomato: Tomato? = tomatoAdapter.currentList[position];
        val intent = Intent(this, TomatoActivity::class.java)
        if(tomato == null) return;
        intent.putExtra("name", tomato.name)
        intent.putExtra("pauseTime", tomato.pauseTime )
        intent.putExtra("mainTime", tomato.mainTime )
        intent.putExtra("tomatoesAmount", tomato.tomatoAmount )

        startActivityForResult(intent, newTomatoActivityRequestCode)
    }
}