package com.example.brs_cout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null

    protected val binding: VB
        get() = _binding ?: throw IllegalStateException("Binding is null. Is the view destroyed or not yet created?")

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Subclasses can override this method to perform their specific view setup.
        // The 'binding' property is guaranteed to be non-null here.
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding instance to release references to views and prevent memory leaks.
        _binding = null
    }


    fun loadFragment(f: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, f).commit()
    }
}