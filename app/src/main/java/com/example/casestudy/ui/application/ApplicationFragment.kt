package com.example.casestudy.ui.application

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.casestudy.MainActivity
import com.example.casestudy.databinding.FragmentApplicationBinding
import com.example.casestudy.model.App
import com.example.casestudy.model.Category
import com.example.casestudy.ui.home.HomeViewModel


class ApplicationFragment : Fragment(), ApplicationClickListener {
    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentApplicationBinding? = null
    private lateinit var navController: NavController
    private var nameSearchEditText: List<App>? = null
    private var sortRatingValueList: List<App>? = null
    private var sortRatingCountList: List<App>? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApplicationBinding.inflate(inflater, container, false)
        val root: View = _binding!!.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavController(requireContext())
        val selectedCategory = arguments?.getSerializable("selectedCategory") as Category
        (requireActivity() as MainActivity).setToolbarTitle(selectedCategory.title)

        val adapter = ApplicationAdapter(this)
        binding.recyclerView.adapter = adapter
        refreshData()
       // binding.progressBar.visibility = View.VISIBLE
        viewModel.appList.observe(viewLifecycleOwner) {
            adapter.updateAppList(it)
            binding.progressBar.visibility = if (it.size > 0) View.GONE else View.VISIBLE
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.setApplist(listOf())
            refreshData()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.sortByRatingCount.setOnClickListener {
            if (binding.sortByRatingCount.isChecked) {
                sortRatingCountList = viewModel.appList.value?.sortedByDescending { it.ratingCount }
                if (sortRatingCountList != null) {
                    adapter.updateAppList(sortRatingCountList!!)
                    binding.sortByRatingValue.isChecked = false
                    Log.d(TAG, "onViewCreated: ${sortRatingCountList!!}")
                }
            } else {
                refreshData()
            }
        }
        binding.sortByRatingValue.setOnClickListener {
            if (binding.sortByRatingValue.isChecked) {
                sortRatingValueList = viewModel.appList.value?.sortedByDescending { it.ratingValue }
                if (sortRatingValueList != null) {
                    adapter.updateAppList(sortRatingValueList!!)
                    binding.sortByRatingCount.isChecked = false
                }
            } else {
                refreshData()
            }
        }

        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                filterList(s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                nameSearchEditText?.let {
                    adapter.updateAppList(it)
                }
            }
        })
    }

    fun filterList(searchText: String) {
        nameSearchEditText =
            viewModel.appList.value?.filter { it.name.contains(searchText, ignoreCase = true) ||  it.packageName.contains(searchText, ignoreCase = true) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onApplicationClicked(app: App) {
        Log.d("APPLICATION", "onApplicationClicked: ${app.packageName}")
        val packageName = app.packageName

        val isAppInstalled = try {
            requireActivity().packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }


        if (!isAppInstalled) {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                    )
                )
            }

        } else {

            val launchIntent =
                requireActivity().packageManager.getLaunchIntentForPackage(packageName)
            startActivity(launchIntent)
        }

    }

    fun refreshData() {
        val selectedCategory = arguments?.getSerializable("selectedCategory") as Category

        when (selectedCategory) {
            Category.FLASH_LIGHTS -> {
                viewModel.getFlashlights(requireContext()) { showWarningMessage() }
            }
            Category.COLORED_LIGHTS -> {
                viewModel.getColorligts(requireContext()) { showWarningMessage() }
            }
            Category.SOS_ALERTS -> {
                viewModel.getSosalerts(requireContext()) { showWarningMessage() }
            }
        }
    }

    fun showWarningMessage() {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.apply {
            setTitle("Warning")
            setMessage("Network Error")
            setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ ->
                dialog.dismiss()
                Navigation.findNavController(binding.root).popBackStack()

            }
            show()
        }
    }
}
