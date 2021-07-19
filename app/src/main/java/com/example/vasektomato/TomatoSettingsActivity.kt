package com.example.vasektomato

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.vasektomato.databinding.TomatoSettingsBinding

class TomatoSettingsActivity : AppCompatActivity() {
    private lateinit var binding: TomatoSettingsBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TomatoSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOkTomatoSettings.setOnClickListener {
            val name = binding.editTextTomatoName.text.toString()
            intent.putExtra("name", name )
            intent.putExtra("pauseTime", binding.editTextPauseTime.text.toString().toInt() )
            intent.putExtra("mainTime", binding.editTextMainTime.text.toString().toInt() )
            intent.putExtra("tomatoesAmount", binding.editTextTomatoesAmount.text.toString().toInt() )

            setResult(-1, intent)
            finish()

        }
    }

}