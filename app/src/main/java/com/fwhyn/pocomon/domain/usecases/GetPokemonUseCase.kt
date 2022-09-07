package com.fwhyn.pocomon.domain.usecases

import com.fwhyn.pocomon.domain.model.FlavorTextEntry
import com.fwhyn.pocomon.domain.model.Genera
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.repository.RepositoryInterface

class GetPokemonUseCase(
    private val repository: RepositoryInterface,
    private val getSinglePokemonUseCase: GetSinglePokemonUseCase,
    private val getIsPokemonSavedUseCase: GetIsPokemonSavedUseCase
) {

    suspend fun getPokemon(id: Int): Pokemon {
        val isPokemonSaved = getIsPokemonSavedUseCase.isPokemonSaved(id)
        return if (isPokemonSaved) {
            getSinglePokemonUseCase.getSinglePokemon(id)
        } else {
            val pokemon = repository.getPokemon(id)
            val species = repository.getSpecies(id)
            pokemon.genera = getPokemonGenera(species.genera)
            pokemon.description = getPokemonDescription(species.flavor_text_entries)
            pokemon.capture_rate = species.capture_rate
            pokemon
        }
    }

    private fun getPokemonGenera(generaList: List<Genera>?): String {
        var index = 0
        while (generaList?.get(index)?.language?.name != "en") {
            index++
        }
        return generaList[index].genus
    }

    private fun getPokemonDescription(flavorTextList: List<FlavorTextEntry>): String {
        var index = flavorTextList.size - 1
        while (flavorTextList[index].language.name != "en") {
            index--
        }
        var flavorText = flavorTextList[index].flavor_text
        flavorText = flavorText.replace("POKéMON", "Pokémon")
        flavorText = flavorText.replace("\n", " ")
        return flavorText
    }
}