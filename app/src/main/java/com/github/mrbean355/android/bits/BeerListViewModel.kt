package com.github.mrbean355.android.bits

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class BeerListViewModel : ViewModel() {
    private val service = BeerService()
    private val allBeers = mutableListOf<Beer>()
    private val _displayedBeers = MutableLiveData<List<Beer>>()

    val displayedBeers: LiveData<List<Beer>> = _displayedBeers

    fun initialise() {
        viewModelScope.launch(context = IO) {
            val response = service.getBeers()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                displayBeers(body)
            } else {
                Log.e("BeerListViewModel", "Service call failed: $response")
            }
        }
    }

    /** Filter [_displayedBeers] based on some [query]. */
    fun onSearch(query: String?) {
        _displayedBeers.value = allBeers.filter {
            it.name.contains(query.orEmpty(), ignoreCase = true)
        }
    }

    /** Sort, filter & display beers. */
    private fun displayBeers(body: List<Beer>) {
        allBeers.clear()
        allBeers += body.sortedBy { it.name }
        _displayedBeers.postValue(allBeers)
    }
}
