package com.example.biometricsample.activity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.biometricsample.R
import com.example.biometricsample.animations.UTMoneyServiceTypesScreen
import com.example.biometricsample.databinding.FragmentHomeBinding
import com.example.biometricsample.transfers.ui.AddBeneficiaryScreen
import com.example.biometricsample.transfers.ui.TransferListScreen

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.navigationEvent.observe(viewLifecycleOwner, this::navigateTo)
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {

                    TransferListScreen()

                  //  AddBeneficiaryScreen(homeViewModel)
                }

            }

    }

    private fun navigateTo(navigationEvent: NavigationEvent?) {
        when(navigationEvent){
            NavigationEvent.NavigateToFragmentA -> findNavController().navigate(R.id.navigation_dashboard)
            NavigationEvent.NavigateToFragmentB -> TODO()
            NavigationEvent.NavigateToFragmentC -> TODO()
            null -> TODO()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}