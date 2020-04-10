package com.github.mrbean355.android.bits

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import kotlinx.android.synthetic.main.activity_beer_list.*

class BeerListActivity : AppCompatActivity(R.layout.activity_beer_list) {
    private val viewModel by viewModels<BeerListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = BeerAdapter()
        beer_list.adapter = adapter
        beer_list.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        viewModel.initialise()
        viewModel.displayedBeers.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_beer_list, menu)

        val searchView = menu?.findItem(R.id.search)?.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearch(newText)
                return false
            }
        })
        return true
    }
}
