package com.fwhyn.pocomon.domain.model

import com.fwhyn.pocomon.data.utils.DataConstants.Companion.DEFAULT_CAPTURE_RATE
import java.io.Serializable

data class Pokemon(
    val base_experience: Int,
    val height: Int,
    var id: Int,
    var name: String,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int,
    var dominant_color: Int?,
    var genera: String,
    var description: String,
    var capture_rate: Int = DEFAULT_CAPTURE_RATE,
    var url: String? = "",
    var caught: Boolean = false,
    var custom_name: String
) : Serializable {

}