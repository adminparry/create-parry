package com.example.androidapidemo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.androidapidemo.databinding.ActivityApiDetailBinding
import com.example.androidapidemo.viewmodel.ApiDetailViewModel
import com.google.android.material.snackbar.Snackbar

class ApiDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_API_ID = "extra_api_id"
    }

    private lateinit var binding: ActivityApiDetailBinding
    private val viewModel: ApiDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiId = intent.getIntExtra(EXTRA_API_ID, -1)
        if (apiId == -1) {
            Snackbar.make(binding.root, "Invalid API ID", Snackbar.LENGTH_SHORT).show()
            finish()
            return
        }

        setupToolbar()
        observeViewModel()
        viewModel.loadApiDetail(apiId)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun observeViewModel() {
        viewModel.apiDetail.observe(this) { api ->
            api?.let {
                binding.apply {
                    progressBar.isVisible = false
                    contentGroup.isVisible = true
                    
                    toolbarLayout.title = it.name
                    tvCategory.text = it.category
                    tvDescription.text = it.description
                    tvMinSdk.text = "Minimum SDK: ${it.minSdkVersion}"
                    tvExample.text = it.example
                }
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.contentGroup.isVisible = !isLoading
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }
} 