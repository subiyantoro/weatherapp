package com.subiyantoro.panintitest.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.subiyantoro.panintitest.databinding.FragmentSecondScreenBinding
import com.subiyantoro.panintitest.presentation.MainActivity

class SecondScreenFragment: Fragment() {
    lateinit var binding: FragmentSecondScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.finishBtn.setOnClickListener {
            val goToMainPage = Intent(requireContext(), MainActivity::class.java)
            goToMainPage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(goToMainPage)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}