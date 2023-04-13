package fr.mjoudar.oqee.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import fr.mjoudar.oqee.R
import fr.mjoudar.oqee.domain.model.Movie
import java.lang.StringBuilder

class CustomViewBinding {

    companion object {

        /******************************************************************************************
         ** Photo binding
         ******************************************************************************************/
        @JvmStatic
        @BindingAdapter("cover")
        fun bindCover(imgView: ImageView, movie: Movie) {
            if (movie.cover.isNotEmpty()) {
                imgView.load(movie.cover) {
                    placeholder(R.drawable.ic_loading_animation)
                    crossfade(true)
                    error(R.drawable.broken_image)
                }
            } else imgView.load(R.drawable.movie_placeholder)
        }

        /******************************************************************************************
         ** Photo binding
         ******************************************************************************************/
        @JvmStatic
        @BindingAdapter("info")
        fun bindInfo(textView: TextView, movie: Movie) {
            val builder = buildString {
                if (movie.releaseDate.isNotEmpty()) {
                    append(movie.releaseDate)
                }
                if (movie.genre.isNotEmpty()) {
                    if (this.isNotEmpty()) append("  |  ")
                    for (i in movie.genre) {
                        append(i)
                        append(", ")
                    }  // der,
                    setLength(this.length-2)
                }
                if (movie.duration.isNotEmpty()) {
                    if (this.isNotEmpty()) append("  |  ")
                    append(movie.duration)
                }
            }
            textView.text = builder
        }

    }
}