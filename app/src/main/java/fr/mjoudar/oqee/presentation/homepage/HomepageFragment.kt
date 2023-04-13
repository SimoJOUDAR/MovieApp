package fr.mjoudar.oqee.presentation.homepage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import fr.mjoudar.oqee.R
import fr.mjoudar.oqee.databinding.FragmentHomepageBinding
import fr.mjoudar.oqee.domain.model.Movie
import fr.mjoudar.oqee.presentation.adapters.MoviesAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomepageFragment : Fragment() {

    private var _binding: FragmentHomepageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomepageViewModel by activityViewModels()
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        collectDevices()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /***********************************************************************************************
     ** UI
     ***********************************************************************************************/

    // Set the RecyclerView's Adapter and LayoutManager
    private fun setRecyclerView() {
        val onItemClickListener = View.OnClickListener { itemView ->
            val movie = itemView.tag as Movie
            findNavController().navigate(HomepageFragmentDirections.actionHomepageFragmentToDetailFragment(movie))
        }

        val onContextClickListener = View.OnContextClickListener { true }

        adapter = MoviesAdapter(onItemClickListener, onContextClickListener)
        binding.recyclerview.adapter = adapter
    }

    private fun displayIsLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerview.visibility = View.INVISIBLE
        binding.errorLayout.errorPage.visibility = View.GONE
    }

    private fun displayError(e : Exception) {
        binding.errorLayout.errorText.text = e.toString()
        binding.progressBar.visibility = View.GONE
        binding.recyclerview.visibility = View.GONE
        binding.errorLayout.errorPage.visibility = View.VISIBLE
    }

    private fun displayData(data: List<Movie>) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerview.visibility = View.VISIBLE
        binding.errorLayout.errorPage.visibility = View.GONE
        ((binding.recyclerview.adapter) as MoviesAdapter).submitList(data)
    }

    /***********************************************************************************************
     ** Subscribers
     ***********************************************************************************************/

    private fun collectDevices() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesStateFlow.collectLatest {
                    when (it) {
                        is MoviesUiState.Loading -> displayIsLoading()
                        is MoviesUiState.Error -> displayError(it.error)
                        is MoviesUiState.Success -> displayData(it.movies)
                    }
                }
            }
        }
    }


}