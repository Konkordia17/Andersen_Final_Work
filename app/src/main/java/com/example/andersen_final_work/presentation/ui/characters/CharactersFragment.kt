package com.example.andersen_final_work.presentation.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andersen_final_work.Contract
import com.example.andersen_final_work.R
import com.example.andersen_final_work.app.App
import com.example.andersen_final_work.databinding.FragmentCharactersBinding
import com.example.andersen_final_work.extensions.getCurrentPosition
import com.example.andersen_final_work.domain.models.Filter
import javax.inject.Inject

class CharactersFragment : Fragment(R.layout.fragment_characters) {
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var charactersAdapter: CharactersAdapter
    @Inject
    lateinit var vmFactory: CharactersViewModelFactory
    val viewModel by viewModels<CharactersViewModel>(factoryProducer = { vmFactory })

    var currentFilter = Filter("", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel.getCharacters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModel()
        binding.searchCharacters.addTextChangedListener {
            val enteredText = binding.searchCharacters.text.toString()
            viewModel.findByName(enteredText)
        }
        binding.filterListButton.setOnClickListener {
            CharactersDialogFragment.newInstance(currentFilter)
                .show(childFragmentManager, "CharacterDialog")
        }
    }

    private fun observeViewModel() {
        viewModel.characters.observe(viewLifecycleOwner) {
            charactersAdapter.submitList(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.isLoadingData.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), Contract.NOT_DATA, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initList() {
        charactersAdapter = CharactersAdapter { character ->
            val action =
                CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
                    character
                )
            findNavController().navigate(action)
        }
        with(binding.charactersList) {
            adapter = charactersAdapter
            layoutManager = GridLayoutManager(context, 2)
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            setHasFixedSize(true)
            setOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val position = getCurrentPosition()
                    viewModel.onPositionChanged(position)
                }
            })
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.updateAfterSwipe()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    fun filter(filter: Filter) {
        viewModel.findBySpecies(filter.species)
        viewModel.findByStatus(filter.status)
        viewModel.findByGender(filter.gender)
        currentFilter = Filter(filter.species, filter.status, filter.gender)
    }
}