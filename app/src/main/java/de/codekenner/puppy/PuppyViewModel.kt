package de.codekenner.puppy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PuppyViewModel : ViewModel() {

    private var currentListData = PuppyList()

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(currentListData)
    val viewState: LiveData<ViewState> = _viewState

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

    fun onViewEvent(viewEvent: ViewEvent) {
        when (viewEvent) {
            is FilterChanged -> onFilterChanged(viewEvent.filter)
            is PuppySelected -> onItemSelected(viewEvent.puppy)
        }
    }

    private fun onFilterChanged(filter: String) {
        updateList(PuppyList(puppies = PuppyRepository.findPuppies(filter), filter = filter))
    }

    private fun onItemSelected(puppy: Puppy) {
        _viewState.value = PuppyDetails(puppy)
    }
}