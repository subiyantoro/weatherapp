package com.subiyantoro.panintitest.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.subiyantoro.panintitest.R
import com.subiyantoro.panintitest.databinding.FragmentFirstScreenBinding

class FirstScreen: Fragment() {
    lateinit var binding: FragmentFirstScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.vp_onboard)
        binding.nextBtn.setOnClickListener {
            viewPager?.currentItem = 1
        }
        super.onViewCreated(view, savedInstanceState)
    }
}