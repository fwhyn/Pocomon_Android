package com.fwhyn.pocomon.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwhyn.pocomon.data.utils.DataConstants
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.model.PokemonResults
import com.fwhyn.pocomon.domain.usecases.GetAllLocalPokemonUseCase
import com.fwhyn.pocomon.domain.usecases.GetAllPokemonNamesUseCase
import com.fwhyn.pocomon.domain.usecases.GetIsPokemonCaughtUseCase
import com.fwhyn.pocomon.domain.usecases.GetPokemonUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class HomeViewModel(
    private val getPokemonUseCase: GetPokemonUseCase,
    private val getIsPokemonCaughtUseCase: GetIsPokemonCaughtUseCase,
    private val getAllPokemonNamesUseCase: GetAllPokemonNamesUseCase,
    private val getAllLocalPokemonUseCase: GetAllLocalPokemonUseCase
) : ViewModel(), KoinComponent {
    var failure = 0

    private var coroutineExceptionHandler: CoroutineExceptionHandler
    private var job: Job = Job()

    private val allLoadedPokemons: MutableList<Pokemon> = mutableListOf()
    val allPokemonsToLoad: MutableList<Pokemon> = mutableListOf()

    private val _myPokemonNamesList: MutableLiveData<Result<MutableList<Pokemon>>> = MutableLiveData()
    val myPokemonNamesList: LiveData<Result<MutableList<Pokemon>>>
        get() = _myPokemonNamesList

    private val _myPokemons: MutableLiveData<Result<MutableList<Pokemon>>> = MutableLiveData()
    val myPokemons: LiveData<Result<MutableList<Pokemon>>>
        get() = _myPokemons

    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            _myPokemons.value = Result.Failure(exception)

            exception.printStackTrace()
        }

        getAllPokemonNames()
    }

    fun getAllPokemonNames() {
        cancelJobIfRunning()
        job = viewModelScope.launch(coroutineExceptionHandler) {
            _myPokemonNamesList.value = Result.Loading

            allPokemonsToLoad.clear()
            allLoadedPokemons.clear()
            // get from https://pokeapi.co/api/v2/TOTAL_POKEMONS
            val pokemonResults = getAllPokemonNamesUseCase.getAllPokemonNames(DataConstants.TOTAL_POKEMONS)
            allPokemonsToLoad.addAll(convertUrlIoId(pokemonResults))

            // get saved local data
            var localPokemonData: List<Pokemon>
            launch {
                localPokemonData = getAllLocalPokemonUseCase.getAllLocalPokemon()
                allLoadedPokemons.addAll(localPokemonData)
            }

            val limitedToLoadData = getLimitedToLoad( 0)
            loadPokemon(limitedToLoadData)

            _myPokemonNamesList.value = Result.Success(allPokemonsToLoad)
        }
    }

    private fun convertUrlIoId(pokemonResults: PokemonResults): MutableList<Pokemon> {
        val pokemons: MutableList<Pokemon> = mutableListOf()

        pokemonResults.results.forEach {
            // remove "/" in url
            val trimmedUrl = it.url?.dropLast(1)

            // get pokemon id from url
            it.id = trimmedUrl!!.substring(trimmedUrl.lastIndexOf("/") + 1).toInt()

            // make sure that id does not exceed total pokemon limit
            if (it.id <= DataConstants.TOTAL_POKEMONS) pokemons.add(it)
        }

        return pokemons
    }

    fun getLimitedToLoad(firstIndex: Int): MutableList<Pokemon> {
        val toLoadList: MutableList<Pokemon> = mutableListOf()
        var i = firstIndex

        while (toLoadList.size < DataConstants.POKEMONS_LOAD_LIMIT && i < DataConstants.TOTAL_POKEMONS) {
            if (!isCaught(i)) {
                toLoadList.add(allPokemonsToLoad[i])
            }
            i++
        }

        return toLoadList
    }

    fun loadPokemon(pokemons: MutableList<Pokemon>) {
        cancelJobIfRunning()

        job = viewModelScope.launch(coroutineExceptionHandler) {
            _myPokemons.value = Result.Loading

            coroutineScope {
                pokemons.forEach {
                    launch(coroutineExceptionHandler) {
                        if (!containPokemons(allLoadedPokemons, it)) {
                            // get pokemon from local or repository
                            val pokemon = getPokemonUseCase.getPokemon(it.id)

                            // add pokemon list to viewmodel object
                            allLoadedPokemons.add(pokemon)
                        }
                    }
                }
            }
            allLoadedPokemons.sortBy { it.id }

            _myPokemons.value = Result.Success(allLoadedPokemons)
        }
    }

    fun isPokemonCaught(id: Int): Boolean {
        return getIsPokemonCaughtUseCase.isPokemonCaught(id)
    }

    private fun containPokemons(pokemonList: MutableList<Pokemon>, pokemon: Pokemon): Boolean {
        pokemonList.forEach {
            if (it.id == pokemon.id) return true
        }
        return false
    }

    private fun isCaught(index: Int): Boolean {
//        allLoadedPokemons.forEach {
//            if (it.id == allPokemonsToLoad[index].id) {
//                return it.caught
//            }
//        }
        return false
    }

    private fun cancelJobIfRunning() {
        if (job.isActive) {
            job.cancel()
        }
    }

    sealed class Result<out T : Any> {
        data class Success<out T : Any>(val value: T) : Result<T>()
        object Loading : Result<Nothing>()
        data class Failure(val throwable: Throwable) : Result<Nothing>()
    }
}
