package com.example.scrollablexml.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.scrollablexml.adapter.MainAdapter
import com.example.scrollablexml.databinding.FragmentListBinding
import com.example.scrollablexml.viewmodel.RiderListViewModel
import com.example.scrollablexml.viewmodel.RiderListViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RiderListViewModel by viewModels {
        RiderListViewModelFactory("Kamen Rider List")
    }

    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        mainAdapter = MainAdapter(
            initialList = emptyList(),
            onImdbClick = { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                    setPackage("com.android.chrome")
                }
                startActivity(intent)
            },
            onDetailClick = { rider ->
                viewModel.onRiderClick(rider)
            }
        )

        binding.recyclerView.adapter = mainAdapter

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.riderList.collectLatest { list ->
                mainAdapter.updateData(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onClickRider.collectLatest { rider ->
                rider?.let {
                    val action = ListFragmentDirections.actionListFragmentToDetailFragment(it.id)
                    findNavController().navigate(action)
                    viewModel.clearRiderClick()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
