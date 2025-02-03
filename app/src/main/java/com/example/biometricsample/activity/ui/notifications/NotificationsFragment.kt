package com.example.biometricsample.activity.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.biometricsample.R
import com.example.biometricsample.databinding.FragmentNotificationsBinding
import com.example.biometricsample.transfers.UTBeneficiaryTypes
import com.example.biometricsample.transfers.ui.AddBeneficiaryScreen
import com.example.biometricsample.transfers.ui.UTGridBeneficiaryTypeListScreen
import com.example.biometricsample.ui.components.UTGridItem

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return ComposeView(requireContext()).apply {
                setContent {
                    AddBeneficiaryScreen()
                }

        }
      //  return root
    }
val onItemClicked :(UTGridItem)->Unit = {item ->  when(item.title){
                UTBeneficiaryTypes.DomesticWire.name -> {
                    findNavController().navigate(R.id.navigation_home)
                }
} }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}