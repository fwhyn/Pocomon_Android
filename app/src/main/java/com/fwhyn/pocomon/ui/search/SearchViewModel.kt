package com.fwhyn.pocomon.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwhyn.pocomon.data.utils.DataConstants
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.model.PokemonResults
import com.fwhyn.pocomon.domain.usecases.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class SearchViewModel(
    private val addCaughtPokemonUseCase: AddCaughtPokemonUseCase,
    private val removeCaughtPokemonUseCase: RemoveCaughtPokemonUseCase,
    private val getIsPokemonCaughtUseCase: GetIsPokemonCaughtUseCase,
    private val getAllPokemonNamesUseCase: GetAllPokemonNamesUseCase,
    private val getPokemonUseCase: GetPokemonUseCase
) : ViewModel(), KoinComponent {

    private var coroutineExceptionHandler: CoroutineExceptionHandler
    private var job: Job = Job()
    val list: MutableList<Pokemon> = mutableListOf()

    private val _myPokemonNamesList: MutableLiveData<Result<PokemonResults>> = MutableLiveData()
    val myPokemonNamesList: LiveData<Result<PokemonResults>>
        get() = _myPokemonNamesList

    private val _mySearchedPokemon: MutableLiveData<Result<MutableList<Pokemon>>> = MutableLiveData()
    val mySearchedPokemon: LiveData<Result<MutableList<Pokemon>>>
        get() = _mySearchedPokemon

    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            _myPokemonNamesList.value = Result.Failure(exception)
        }
        getAllPokemonNames()
    }

    fun addCaughtPokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            addCaughtPokemonUseCase.addCaughtPokemon(pokemon)
        }
    }

    fun isPokemonCaught(id: Int): Boolean {
        return getIsPokemonCaughtUseCase.isPokemonCaught(id)
    }

    fun getAllPokemonNames() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _myPokemonNamesList.value = Result.Loading
            _myPokemonNamesList.value =
                Result.Success(getAllPokemonNamesUseCase.getAllPokemonNames(DataConstants.TOTAL_POKEMONS))
        }
    }

    fun getPokemon(pokemonList: MutableList<Pokemon>) {
        cancelJobIfRunning()
        job = viewModelScope.launch(coroutineExceptionHandler) {
            _mySearchedPokemon.value = Result.Loading
            coroutineScope {
                pokemonList.forEach {
                    launch(coroutineExceptionHandler) {
                        if (!checkIfContainsPokemon(list, it)) list.add(getPokemonUseCase.getPokemon(it.id))
                    }
                }
            }
            list.sortBy { it.id }
            _mySearchedPokemon.value = Result.Success(list)
        }
    }

    private fun checkIfContainsPokemon(pokemonList: MutableList<Pokemon>, pokemon: Pokemon): Boolean {
        pokemonList.forEach {
            if (it.id == pokemon.id) return true
        }
        return false
    }

    fun cancelJobIfRunning() {
        if (job.isActive) {
            job.cancel()
            _mySearchedPokemon.value = Result.Success(mutableListOf())
        }
    }

    sealed class Result<out T : Any> {
        data class Success<out T : Any>(val value: T) : Result<T>()
        object Loading : Result<Nothing>()
        data class Failure(val throwable: Throwable) : Result<Nothing>()
    }
}

