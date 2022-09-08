package com.fwhyn.pocomon.ui.info

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.fwhyn.pocomon.R
import com.fwhyn.pocomon.databinding.FragmentInfoBinding
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.domain.model.Type
import com.fwhyn.pocomon.ui.common.dialog.CustomDialog
import com.fwhyn.pocomon.ui.common.dialog.CustomDialogManager
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.CATCH_DIALOG
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoFragment : Fragment(), CustomDialog.DialogCallback, CustomDialog.ClickListener {
    private lateinit var viewBinding: FragmentInfoBinding
    private lateinit var pokemon: Pokemon
    private lateinit var dialogManager: CustomDialogManager

    private val viewModel by viewModel<InfoViewModel>()
    private var dominantColor = Color.GRAY

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.setShowHideAnimationEnabled(false)
        setObserver(activity as FragmentActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentInfoBinding.inflate(inflater, container, false)
        arguments?.let { bundle ->
            pokemon = InfoFragmentArgs.fromBundle(bundle).pokemon
            setData()
        }
        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        activity?.window?.statusBarColor = dominantColor
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)?.visibility =
            View.GONE
        activity?.window?.navigationBarColor = dominantColor
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.bg_color)
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)?.visibility =
            View.VISIBLE
        activity?.window?.navigationBarColor =
            ContextCompat.getColor(requireContext(), R.color.bg_color)
    }

    private fun setObserver(fragmentActivity: FragmentActivity) {
        dialogManager = CustomDialogManager.initDialog(fragmentActivity, this)
    }

    private fun setData() {
        with(viewBinding) {
            pokeId.text = requireContext().getString(R.string.pokemon_number_format, pokemon.id)
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
            pokeBack.setOnClickListener { activity?.onBackPressed() }
            setPokemonTypes(pokemon.types)
            if (viewModel.isPokemonFavorite(pokemon.id)) setFavoriteIcon(pokeFav)
            loadImage(pokeInfoImage, pokemon.sprites.other.official_artwork.front_default)
            pokeFav.setOnClickListener { favoriteButtonClickListener(pokeFav) }
            dominantColor = pokemon.dominant_color!!
            pokeScrollView.setBackgroundColor(dominantColor)
            activity?.window?.statusBarColor = dominantColor
        }
    }

    private fun favoriteButtonClickListener(pokeFav : ImageView){
        if(viewModel.caught){
            viewModel.deleteFavoritePokemon(pokemon)
            removeFavoriteIcon(pokeFav)
        } else{
//            dialogManager.showDialog(CATCH_DIALOG)
            viewModel.addFavoritePokemon(pokemon)
            setFavoriteIcon(viewBinding.pokeFav)
        }
    }

    private fun setFavoriteIcon(imageView: ImageView) {
        imageView.setImageResource(R.drawable.remove_icon)
//        imageView.tag = getString(R.string.favorite_tag)
    }

    private fun removeFavoriteIcon(imageView: ImageView) {
        imageView.setImageResource(R.drawable.app_icon)
//        imageView.tag = getString(R.string.not_favorite_tag)
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
        Glide.with(requireContext().applicationContext)
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
//                val inflater = LayoutInflater.from(activity)
//                val updateView = inflater.inflate(R.layout.progress_layout, null)
                builder = CustomDialog.Builder()
                    .setTitle("test title")
                    .setMessage("test message")
                    .setPositiveButton("Ok", this)
                    .setNegativeButton("Cancel", this)
                    .setNotDefaultButtonStyle(true)
            }
            else -> {}
        }
        return builder
    }

    override fun onClickDialog(tag: String?, whichButton: Int) {
        when (tag) {
            CATCH_DIALOG -> when (whichButton) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.addFavoritePokemon(pokemon)
                    setFavoriteIcon(viewBinding.pokeFav)
                }
            }
            else -> {}
        }
    }
}