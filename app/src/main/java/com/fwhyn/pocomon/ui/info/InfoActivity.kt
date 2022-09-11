package com.fwhyn.pocomon.ui.info

import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
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
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.DELETE_DIALOG
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.INFO_ACTIVITY_CODE
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.LOADING_DIALOG
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.POKEMON_KEY
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.SAVE_TAG
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.TAG
import com.fwhyn.pocomon.ui.utils.UiUtil.Companion.disableEditText
import com.fwhyn.pocomon.ui.utils.UiUtil.Companion.enableEditText
import org.koin.androidx.viewmodel.ext.android.viewModel


// TODO(after remove caught -> back)
// TODO(can rename caught item)
// TODO(add loading while catching pokemon and load pokemon info)
class InfoActivity : AppCompatActivity(), CustomDialog.DialogCallback, CustomDialog.ClickListener {
    private lateinit var viewBinding: ActivityInfoBinding
    private lateinit var pokemon: Pokemon

    private val viewModel by viewModel<InfoViewModel>()

    private var dominantColor = Color.GRAY
    private var activityCode = DEFAULT_ACTIVITY_CODE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityInfoBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Pocomon)
        setContentView(viewBinding.root)

        setObserver(this)
        init()
        setButtons()
    }

    // functions
    private fun setObserver(fragmentActivity: FragmentActivity) {
        with(viewBinding) {
            viewModel.jobs.observe(this@InfoActivity) {
                val loading: Boolean = viewModel.jobsData[it] == true

                when(it) {
                    CATCH_DIALOG, DELETE_DIALOG -> {
                        if (loading) {
//                            viewModel.showDialog(LOADING_DIALOG)
                        } else {
//                            viewModel.dismissDialog(LOADING_DIALOG)
                            viewModel.jobsData.remove(it)

                            setResult(INFO_ACTIVITY_CODE)
                            finish()
                        }
                    }
                    else -> {
//                        if (viewModel.jobsData.size > 0) {
//                            viewModel.showDialog(LOADING_DIALOG)
//                        } else {
//                            viewModel.dismissDialog(LOADING_DIALOG)
//                            viewModel.jobsData.remove(it)
//                        }
                    }
                }
            }

            viewModel.caught.observe(this@InfoActivity) {
                if (it) {
                    delete.visibility = VISIBLE
                    catchButton.visibility = GONE
                    edit.visibility = VISIBLE
                } else {
                    delete.visibility = GONE
                    catchButton.visibility = VISIBLE
                    edit.visibility = GONE
                }
            }

            viewModel.editMode.observe(this@InfoActivity) {
                if (it) {
                    save.visibility = VISIBLE
                    enableEditText(pokeName)
                } else {
                    save.visibility = GONE
                    disableEditText(pokeName)
                }
            }
        }

        CustomDialogManager.initDialog(fragmentActivity, this, viewModel)
    }

    private fun init() {
        with(intent.extras) {
            let {
                activityCode = this?.getInt(ACTIVITY_CODE_KEY) ?: DEFAULT_ACTIVITY_CODE

                this?.getSerializable(POKEMON_KEY)?.let {
                    try {
                        pokemon = it as Pokemon
                        setViews()
                        viewModel.isPokemonCaught(pokemon.id)
                    } catch (e: ClassCastException) {
                        e.printStackTrace()
                        Log.e(TAG, "Pokemon data is not found")
                    }
                }
            }
        }
    }

    private fun setViews() {
        with(viewBinding) {
            pokeId.text = getString(R.string.pokemon_number_format, pokemon.id)
            pokeName.setText(pokemon.name)
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
            setPokemonTypes(pokemon.types)
            loadImage(pokeInfoImage, pokemon.sprites.other.official_artwork.front_default)

            // set theme color
            dominantColor = pokemon.dominant_color!!
            pokeScrollView.setBackgroundColor(dominantColor)
            window?.statusBarColor = dominantColor
        }
    }

    private fun setButtons() {
        with(viewBinding) {
            catchButton.setOnClickListener {
                viewModel.showDialog(CATCH_DIALOG)
            }

            delete.setOnClickListener {
                viewModel.showDialog(DELETE_DIALOG)
            }

            save.setOnClickListener {
                viewModel.renameCaughtPokemonName(pokemon, pokeName.text.toString(), SAVE_TAG)
            }

            edit.setOnClickListener {
                viewModel.setEditMode(true)
            }
        }
    }

    private fun setPokemonTypes(types : List<Type>){
        with(viewBinding){
            if(types.size > 1){
                pokeInfoTypeTwo.text = types[1].type.name
                pokeInfoTypeTwo.visibility = VISIBLE
            } else{
                pokeInfoTypeTwo.visibility = GONE
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

    // other callbacks
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
                    .setPositiveButton(getString(R.string.ok), this)
                    .setNegativeButton(getString(R.string.cancel), this)
                    .setNotDefaultButtonStyle(true)
                    .setView(view)
            }
            DELETE_DIALOG -> {
                builder = CustomDialog.Builder()
                    .setMessage(String.format(getString(R.string.delete_confirmation), pokemon.name))
                    .setPositiveButton(getString(R.string.ok), this)
                    .setNegativeButton(getString(R.string.cancel), this)
                    .setNotDefaultButtonStyle(true)
            }
            LOADING_DIALOG -> {
                val inflater = LayoutInflater.from(this)
                val view = inflater.inflate(R.layout.dialog_progress, null)
                view.findViewById<TextView>(R.id.percent).visibility = GONE
                view.findViewById<ProgressBar>(R.id.progress_percent).visibility = GONE

                builder = CustomDialog.Builder().setView(view)
            }
            else -> {}
        }
        return builder
    }

    override fun onClickDialog(tag: String?, whichButton: Int) {
        when (tag) {
            CATCH_DIALOG -> when (whichButton) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.addCaughtPokemon(pokemon, tag)
                }
            }
            DELETE_DIALOG -> when (whichButton) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteCaughtPokemon(pokemon, tag)
                }
                else -> {}
            }
        }
    }
}