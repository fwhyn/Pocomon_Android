package com.fwhyn.pocomon.domain.model

import com.fwhyn.pocomon.domain.model.Pokemon
import com.google.gson.annotations.SerializedName

data class TypePokemon(@SerializedName("pokemon") val pokemon: Pokemon)