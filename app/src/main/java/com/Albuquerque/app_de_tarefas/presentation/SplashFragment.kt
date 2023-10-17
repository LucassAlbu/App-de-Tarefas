package com.Albuquerque.app_de_tarefas.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.Albuquerque.app_de_tarefas.R
import com.Albuquerque.app_de_tarefas.databinding.SplashFragmentBinding

class SplashFragment : Fragment() {


    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


}