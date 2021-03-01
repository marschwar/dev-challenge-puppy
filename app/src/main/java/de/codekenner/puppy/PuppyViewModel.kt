package de.codekenner.puppy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PuppyViewModel : ViewModel() {

    private var currentListData = PuppyList()

    private val _viewState: MutableLiveData<ApplicationViewState> = MutableLiveData(currentListData)
    val viewState: LiveData<ApplicationViewState> = _viewState

    init {
        updateList(PuppyList(puppies = PuppyRepository.findPuppies(), filter = ""))
    }

    private fun updateList(puppyList: PuppyList) {
        currentListData = puppyList
        _viewState.value = currentListData
    }

    fun onBackPressed(): Boolean {
        if (viewState.value is PuppyDetails) {
            _viewState.value = currentListData
            return false
        }
        return true
    }

    fun onFilterChanged(filter: String) {
        updateList(PuppyList(puppies = PuppyRepository.findPuppies(filter), filter = filter))
    }

    fun onItemSelected(id: Int) {
        _viewState.value = PuppyDetails(PuppyRepository.findById(id))
    }
}