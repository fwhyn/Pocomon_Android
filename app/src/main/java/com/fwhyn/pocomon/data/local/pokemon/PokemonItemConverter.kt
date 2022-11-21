package com.fwhyn.pocomon.data.local.pokemon

import com.fwhyn.pocomon.domain.model.*

class PokemonItemConverter {
    // pokemon to room
    fun pokemonToRoomPokemon(pokemon: Pokemon): RoomPokemonEntity {
        return RoomPokemonEntity(
            pokemon.id,
            pokemon.base_experience,
            pokemon.height,
            pokemon.name,
            getPokemonSpritesString(pokemon.sprites),
            getPokemonStatsString(pokemon.stats),
            getPokemonTypesString(pokemon.types),
            pokemon.weight,
            pokemon.dominant_color,
            pokemon.genera,
            pokemon.description,
            pokemon.capture_rate,
            pokemon.caught,
            pokemon.custom_name
        )
    }

    // room to pokemon
    fun roomPokemonToPokemon(roomPokemonEntity: RoomPokemonEntity) : Pokemon {
        return Pokemon(
            roomPokemonEntity.base_experience,
            roomPokemonEntity.height,
            roomPokemonEntity.id,
            roomPokemonEntity.name,
            getPokemonSprites(roomPokemonEntity.sprites),
            getPokemonStats(roomPokemonEntity.stats),
            getPokemonTypes(roomPokemonEntity.types),
            roomPokemonEntity.weight,
            roomPokemonEntity.dominant_color,
            roomPokemonEntity.genera,
            roomPokemonEntity.description,
            roomPokemonEntity.capture_rate,
            "",
            roomPokemonEntity.is_caught,
            roomPokemonEntity.custom_name
        )
    }

    // other functions
    private fun getPokemonSpritesString(sprites: Sprites) : String {
        return sprites.other.official_artwork.front_default
    }

    private fun getPokemonTypesString(types: List<Type>) : String {
        val typesString = StringBuilder("")
        for(i in types.indices) {
            typesString.append(types[i].type.name)
            if(i != types.size-1){
                typesString.append(",")
            }
        }
        return typesString.toString()
    }

    private fun getPokemonStatsString(stats : List<Stat>) : String{
        val statsString = StringBuilder("")
        for(i in stats.indices) {
            statsString.append(stats[i].base_stat.toString())
            if(i != stats.size-1){
                statsString.append(",")
            }
        }
        return statsString.toString()
    }

    private fun getPokemonSprites(spritesString: String) : Sprites {
        return Sprites(Other(OfficialArtwork(spritesString)))
    }

    private fun getPokemonTypes(typesString: String) : List<Type> {
        val typesStringList = typesString.split(",")
        val typesList = mutableListOf<Type>()
        typesStringList.forEach {
            typesList.add(Type(TypeX(it)))
        }
        return typesList
    }

    private fun getPokemonStats(statsString: String) : List<Stat>{
        val statsStringList = statsString.split(",")
        val statsList = mutableListOf<Stat>()
        statsStringList.forEach {
            statsList.add(Stat(it.toInt()))
        }
        return statsList
    }
}