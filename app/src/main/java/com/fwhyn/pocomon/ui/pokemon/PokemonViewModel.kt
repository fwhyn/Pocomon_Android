package com.fwhyn.pocomon.ui.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PokemonViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my pokemon Fragment"
    }
    val text: LiveData<String> = _text
}