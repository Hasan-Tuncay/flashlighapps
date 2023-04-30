package com.example.casestudy.ui.home


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.casestudy.R
import com.example.casestudy.databinding.FragmentHomeBinding
import com.example.casestudy.model.Category

class HomeFragment : Fragment() {
    val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.flashLights.setOnClickListener {
            setCategoryAndNavigate(Category.FLASH_LIGHTS)
        }
        binding.coloredLights.setOnClickListener {
            setCategoryAndNavigate(Category.COLORED_LIGHTS)

        }
        binding.sosAlerts.setOnClickListener {

            setCategoryAndNavigate(Category.SOS_ALERTS)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setCategoryAndNavigate(category: Category) {
        viewModel.setApplist(mutableListOf())

        viewModel.setSelectedCategory(category)

        val bundle = Bundle()
        bundle.putSerializable("selectedCategory", category)
        Navigation.findNavController(binding.root).navigate(R.id.nav_application, bundle)


    }
}