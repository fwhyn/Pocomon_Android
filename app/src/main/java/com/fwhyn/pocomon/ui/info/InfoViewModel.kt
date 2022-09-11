package com.fwhyn.pocomon.ui.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.usecases.*
import com.fwhyn.pocomon.domain.utils.loader.Loader
import com.fwhyn.pocomon.ui.common.dialog.CustomDialogManager
import com.fwhyn.pocomon.ui.utils.UiConstant
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class InfoViewModel(
    private val addCaughtPokemonUseCase: AddCaughtPokemonUseCase,
    private val removeCaughtPokemonUseCase: RemoveCaughtPokemonUseCase,
    private val modifyCaughtPokemonUseCase: ModifyCaughtPokemonUseCase,
    private val renameCaughtPokemonUseCase: RenameCaughtPokemonUseCase,
    private val getIsPokemonCaughtUseCase: GetIsPokemonCaughtUseCase,
) : CustomDialogManager(), KoinComponent {
    val jobs: MutableLiveData<String> = MutableLiveData(TAG)
    val jobsData: HashMap<String, Boolean> = HashMap()
    val caught: MutableLiveData<Boolean> = MutableLiveData()
    val editMode: MutableLiveData<Boolean> = MutableLiveData()

    init {
        caught.value = false
        editMode.value = false
    }

    fun setEditMode(boolean: Boolean) {
        editMode.value = boolean
    }

    private fun setJob(owner: String, boolean: Boolean) {
        jobsData[owner] = boolean
        jobs.value = owner
    }

    fun addCaughtPokemon(pokemon: Pokemon, owner: String) {
        viewModelScope.launch {
            setJob(owner, true)

//            delay(2000)
            addCaughtPokemonUseCase.addCaughtPokemon(pokemon)
            caught.value = true // TODO(change to caught = return the Function)

            setJob(owner, false)
        }
    }

    fun removeCaughtPokemon(id: Int, owner: String) {
        viewModelScope.launch {
            setJob(owner, true)

//            delay(2000)
            removeCaughtPokemonUseCase.removeCaughtPokemon(id)
            caught.value = false // TODO(change to caught = return the Function)

            setJob(owner, false)
        }
    }

    fun renameCaughtPokemonName(pokemon: Pokemon, name: String, owner: String) {
        if (!pokemon.name.equals(name)) {
            viewModelScope.launch {
                setJob(owner, true)

                renameCaughtPokemonUseCase.renameCaughtPokemon(pokemon, name)

                setJob(owner, false)
            }
        }
        editMode.value = false
    }

    fun test() {
        viewModelScope.launch {
            setJob(TAG, true)
        }
    }

    fun isPokemonCaught(id : Int) : Boolean{
        return getIsPokemonCaughtUseCase.isPokemonCaught(id).also { caught.value = it }
    }
}