package com.fwhyn.pocomon.ui.info

import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.fwhyn.pocomon.R
import com.fwhyn.pocomon.databinding.ActivityInfoBinding
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.model.Type
import com.fwhyn.pocomon.ui.common.dialog.CustomDialog
import com.fwhyn.pocomon.ui.common.dialog.CustomDialogManager
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.ACTIVITY_CODE_KEY
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.CATCH_DIALOG
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.DEFAULT_ACTIVITY_CODE
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.INFO_ACTIVITY_CODE
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.POKEMON_KEY
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.TAG
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO(after remove caught -> back)
// TODO(can rename caught item)
// TODO(add loading while catching pokemon and load pokemon info)
class InfoActivity : AppCompatActivity(), CustomDialog.DialogCallback, CustomDialog.ClickListener {
    private lateinit var viewBinding: ActivityInfoBinding
    private lateinit var pokemon: Pokemon
    private lateinit var dialogManager: CustomDialogManager

    private val viewModel by viewModel<InfoViewModel>()

    private var dominantColor = Color.GRAY
    private var activityCode = DEFAULT_ACTIVITY_CODE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityInfoBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Pocomon)
        setContentView(viewBinding.root)

        setObserver(this)

        with(intent.extras) {
            let {
                this?.getSerializable(POKEMON_KEY)?.let {
                    try {
                        pokemon = it as Pokemon
                        setViews()
                    } catch (e: ClassCastException) {
                        e.printStackTrace()
                        Log.e(TAG, "Pokemon data is not found")
                    }
                }

                activityCode = this?.getInt(ACTIVITY_CODE_KEY) ?: DEFAULT_ACTIVITY_CODE
            }
        }
    }

    private fun setObserver(fragmentActivity: FragmentActivity) {
        dialogManager = CustomDialogManager.initDialog(fragmentActivity, this)
    }

    private fun setViews() {
        with(viewBinding) {
            pokeId.text = getString(R.string.pokemon_number_format, pokemon.id)
            pokeName.text = pokemon.name
            pokeGenera.text = pokemon.genera
            pokeInfoDesc.text = pokemon.description
            pokeCaptureRate.text = pokemon.capture_rate.toString()
            pokeXp.text = pokemon.base_experience.toString()
            pokeHeight.text = getString(R.string.height_format, (pokemon.height.times(10)))
            pokeWeight.text = getString(R.string.weight_format, (pokemon.weight.div(10.0)))
            pokeHp.text = pokemon.stats[0].base_stat.toString()
            pokeAttack.text = pokemon.stats[1].base_stat.toString()
            pokeDefense.text = pokemon.stats[2].base_stat.toString()
            pokeSpecialAttack.text = pokemon.stats[3].base_stat.toString()
            pokeSpecialDefense.text = pokemon.stats[4].base_stat.toString()
            pokeSpeed.text = pokemon.stats[5].base_stat.toString()
            pokeInfoTypeOne.text = pokemon.types[0].type.name
            pokeBack.setOnClickListener { onBackPressed() }
            setPokemonTypes(pokemon.types)
            if (viewModel.isPokemonCaught(pokemon.id)) setCaughtIcon(pokeFav)
            loadImage(pokeInfoImage, pokemon.sprites.other.official_artwork.front_default)
            pokeFav.setOnClickListener { caughtButtonClickListener(pokeFav) }
            dominantColor = pokemon.dominant_color!!
            pokeScrollView.setBackgroundColor(dominantColor)
            window?.statusBarColor = dominantColor
        }
    }

    private fun caughtButtonClickListener(pokeFav : ImageView){
        if(viewModel.caught){
            viewModel.deleteCaughtPokemon(pokemon)
            removeCaughtIcon(pokeFav)
        } else{
            dialogManager.showDialog(CATCH_DIALOG)
        }
    }

    private fun setCaughtIcon(imageView: ImageView) {
        imageView.setImageResource(R.drawable.remove_icon)
    }

    private fun removeCaughtIcon(imageView: ImageView) {
        imageView.setImageResource(R.drawable.app_icon)
    }

    private fun setPokemonTypes(types : List<Type>){
        with(viewBinding){
            if(types.size>1){
                pokeInfoTypeTwo.text = types[1].type.name
                pokeInfoTypeTwo.visibility = View.VISIBLE
            } else{
                pokeInfoTypeTwo.visibility = View.GONE
            }
        }
    }

    private fun loadImage(pokeInfoImage: ImageView, imageUrl: String) {
        Glide.with(applicationContext)
            .asBitmap()
            .load(imageUrl)
            .centerCrop()
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }


                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(pokeInfoImage)
    }

    // other callback
    override fun onCreateDialog(tag: String?): CustomDialog.Builder? {
        var builder: CustomDialog.Builder? = null
        when (tag) {
            CATCH_DIALOG -> {
                val inflater = LayoutInflater.from(this)
                val view = inflater.inflate(R.layout.dialog_caught_pokemon, null)
                view.findViewById<ImageView>(R.id.poke_image).setImageDrawable(viewBinding
                    .pokeInfoImage.drawable)

                builder = CustomDialog.Builder()
                    .setMessage("Yo caught ${pokemon.name}")
                    .setPositiveButton("Ok", this)
                    .setNegativeButton("Cancel", this)
                    .setNotDefaultButtonStyle(true)
                    .setView(view)
            }
            else -> {}
        }
        return builder
    }

    override fun onClickDialog(tag: String?, whichButton: Int) {
        when (tag) {
            CATCH_DIALOG -> when (whichButton) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.addCaughtPokemon(pokemon)
                    setCaughtIcon(viewBinding.pokeFav)

                    setResult(INFO_ACTIVITY_CODE)
                    finish()
                }
            }
            else -> {}
        }
    }
}