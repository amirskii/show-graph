package com.example.showgraph.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias FragmentBindingInflater<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

open class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: FragmentBindingInflater<VB>
) : Fragment() {

    private var _binding: VB? = null

    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = bindingInflater.invoke(inflater, container, false)
        .apply { _binding = this }
        .root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
            .show()
    }
    fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
            .show()
    }

}