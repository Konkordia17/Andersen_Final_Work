package com.example.andersen_final_work.presentation.ui.episode_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.andersen_final_work.Contract
import com.example.andersen_final_work.R
import com.example.andersen_final_work.app.App
import com.example.andersen_final_work.databinding.FragmentEpisodeDetailBinding
import com.example.andersen_final_work.domain.models.Episode
import com.example.andersen_final_work.presentation.ui.characters.CharactersAdapter
import javax.inject.Inject

class EpisodeDetailFragment : Fragment(R.layout.fragment_episode_detail) {
    private lateinit var binding: FragmentEpisodeDetailBinding
    private var characterAdapter: CharactersAdapter? = null

    @Inject
    lateinit var vmFactory: EpisodeDetailViewModelFactory
    val viewModel by viewModels<EpisodeDetailViewModel>(factoryProducer = { vmFactory })
    private val args: EpisodeDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel.getEpisode(args.episode.episodeId.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initResidentsList()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getEpisode(args.episode.episodeId.toString())
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun observe() {
        viewModel.episode.observe(viewLifecycleOwner) {
            it?.let { bindData(it) } ?: toast(Contract.NOT_DATA_ABOUT_EPISODE)

        }
        viewModel.characters.observe(viewLifecycleOwner) {
            characterAdapter?.submitList(it)
        }
        viewModel.loader.observe(viewLifecycleOwner) {
            binding.episodesDetailProgressBar.isVisible = it
        }
        viewModel.unknown.observe(viewLifecycleOwner) {
            toast(it)
        }
    }

    private fun initResidentsList() {
        characterAdapter = CharactersAdapter { character ->
            val action =
                EpisodeDetailFragmentDirections.actionEpisodeDetailFragmentToCharacterDetailFragment(
                    character
                )
            findNavController().navigate(action)
        }
        with(binding.charactersRV) {
            adapter = characterAdapter
            layoutManager = GridLayoutManager(context, 2)
            val dividerItemDecoration =
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            addItemDecoration(dividerItemDecoration)
            setHasFixedSize(true)
        }
    }

    private fun bindData(episode: Episode) {
        val str = episode.episode.split("E")
        val seasonStr = str[0].substringAfterLast("S")
        val seriesStr = str[1]
        with(binding) {
            nameEpisode.text = episode.name
            airDate.text = episode.air_date
            episodeCreates.text = episode.created
            season.text = seasonStr
            series.text = seriesStr
        }
        viewModel.setCharacters(episode)
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}