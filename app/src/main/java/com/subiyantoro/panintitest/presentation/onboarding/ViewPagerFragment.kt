package com.subiyantoro.panintitest.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.subiyantoro.panintitest.databinding.FragmentViewPagerBinding

class ViewPagerFragment: Fragment() {
    lateinit var binding: FragmentViewPagerBinding
    lateinit var adapter: ViewPagerAdapter
    private val fragmentList = arrayListOf<Fragment>(
        FirstScreen(),
        SecondScreenFragment()
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.vpOnboard.adapter = adapter
        return binding.root
    }
}