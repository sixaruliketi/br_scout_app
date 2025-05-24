package com.example.brs_cout.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.brs_cout.R


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
        init()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding instance to release references to views and prevent memory leaks.
        _binding = null
    }


    fun loadFragment(f: Fragment) {
        // Check if the fragment already exists in the FragmentManager
        // This prevents re-creating it if it's already there
        val existingFragment = parentFragmentManager.findFragmentByTag(tag)

        if (existingFragment != null && existingFragment.isVisible) {
            // Fragment is already visible, do nothing
            return
        }

        val transaction = parentFragmentManager.beginTransaction()

        if (existingFragment == null) {
            // Add the fragment if it's not already in the back stack
            transaction.replace(R.id.main, f, tag)
            // You might add to back stack if you want to navigate back through fragments
            // transaction.addToBackStack(null)
        } else {
            // If it exists but is not visible, show it (and hide others if necessary)
            // For simple navigation, replace is often easier to manage
            transaction.replace(R.id.main, existingFragment, tag)
        }

        transaction.commit()
    }

    abstract fun init()
}