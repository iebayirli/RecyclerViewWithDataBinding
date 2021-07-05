package com.iebayirli.recyclerviewwithdatabinding.scene.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.iebayirli.recyclerviewwithdatabinding.R
import com.iebayirli.recyclerviewwithdatabinding.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            viewModel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
        }
        setContentView(binding.root)

        binding.adapter = MovieAdapter(listOf(), viewModel)


        viewModel.showToast.observe(this) {
            it.getContentIfNotHandled()?.let { movie ->
                Toast.makeText(this, "MovieClicked: ${movie.name}", Toast.LENGTH_SHORT).show()
            }
        }

    }
}