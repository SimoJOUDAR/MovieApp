package fr.mjoudar.oqee.presentation.detail

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.LinearLayoutManager
import fr.mjoudar.oqee.R
import fr.mjoudar.oqee.databinding.FragmentDetailBinding
import fr.mjoudar.oqee.domain.model.Movie
import fr.mjoudar.oqee.presentation.adapters.CrewAdapter


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var player: ExoPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /***********************************************************************************************
     ** UI
     ***********************************************************************************************/

    private fun setRecyclerView() {
        val lm = LinearLayoutManager(requireContext())
        lm.orientation = LinearLayoutManager.HORIZONTAL
        binding.crewRecyclerview.layoutManager = lm
        binding.crewRecyclerview.adapter = CrewAdapter()
    }

    private fun loadData() {
        arguments?.let { it1 ->
            val movie = it1.getParcelable<Movie>(MOVIE_ARG)
            movie?.let { it2 ->
                binding.movie = it2
                submitRecyclerView(it2)
                officialWebSiteListener(it2)
                playButtonListeners(it2)
            }
        }
    }

    private fun submitRecyclerView(movie: Movie) {
        (binding.crewRecyclerview.adapter as CrewAdapter).submitList(movie.castCrew.toCastMemberList())
    }

    /***********************************************************************************************
     ** Listeners
     ***********************************************************************************************/

    private fun playButtonListeners(movie: Movie) {
        binding.playButton.setOnClickListener {
            showVideoDialog(movie)
        }
    }

    private fun showVideoDialog(movie: Movie) {
        if (movie.trailer.isNotEmpty()) {
            val builder = AlertDialog.Builder(requireContext()).create()
            val dialogLayout = layoutInflater.inflate(R.layout.layout_video_dialog, requireActivity().findViewById(androidx.appcompat.R.id.content), false)
            builder.setView(dialogLayout)
            builder.setCanceledOnTouchOutside(true)
            builder.show()

            val uri = Uri.parse(movie.trailer)
            val video = builder.findViewById<PlayerView>(R.id.trailer)

            player = ExoPlayer.Builder(requireContext()).build()
                .also { exoPlayer ->
                    video.player = exoPlayer
                    val mediaItem = MediaItem.fromUri(uri)
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                    exoPlayer.playWhenReady = true
                }

            builder.setOnDismissListener {
                player?.release()
                player = null
                Log.d("OnDismissListener_Log", (player == null).toString())
            }
        } else {
            Toast.makeText(requireContext(), "Trailer unavailable", Toast.LENGTH_SHORT).show()
        }
    }

    private fun officialWebSiteListener(movie: Movie) {
        binding.officialWebsite.setOnClickListener {
            if (movie.officialUrl.isNotEmpty()) {
                val websiteUri: Uri = Uri.parse(movie.officialUrl)
                startActivity(Intent(Intent.ACTION_VIEW, websiteUri))
            }
            else {
                Toast.makeText(requireContext(), "Website unavailable", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val MOVIE_ARG = "movie"
    }
}