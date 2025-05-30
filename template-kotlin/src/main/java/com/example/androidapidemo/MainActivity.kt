package com.example.androidapidemo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidapidemo.adapter.ApiListAdapter
import com.example.androidapidemo.databinding.ActivityMainBinding
import com.example.androidapidemo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val apiAdapter = ApiListAdapter { apiItem ->
        startActivity(
            Intent(this, ApiDetailActivity::class.java).apply {
                putExtra(ApiDetailActivity.EXTRA_API_ID, apiItem.id)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = apiAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }
    }

    private fun observeViewModel() {
        viewModel.apis.observe(this) { apis ->
            apiAdapter.submitList(apis)
            updateEmptyState(apis.isEmpty())
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.error.observe(this) { error ->
            binding.errorView.isVisible = error != null
            binding.errorText.text = error
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchApis(newText.orEmpty())
                return true
            }
        })
        
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                // TODO: Show filter dialog
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.emptyView.isVisible = isEmpty && viewModel.error.value == null
    }
} 