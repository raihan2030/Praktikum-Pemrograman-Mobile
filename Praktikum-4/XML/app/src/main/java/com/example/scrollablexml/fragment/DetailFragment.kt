package com.example.scrollablexml.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.scrollablexml.R
import com.example.scrollablexml.databinding.FragmentDetailBinding
import com.example.scrollablexml.viewmodel.DetailViewModel
import com.example.scrollablexml.viewmodel.DetailViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by lazy {
        val factory = DetailViewModelFactory(args.riderId)
        ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        observeRider()

        return binding.root
    }

    private fun observeRider() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.rider.collectLatest { rider ->
                rider?.let {
                    binding.riderImage.setImageResource(it.posterRes)
                    binding.riderName.text = it.name
                    binding.riderYear.text = getString(R.string.detail_year, it.year)
                    binding.descBody.text = it.description
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
