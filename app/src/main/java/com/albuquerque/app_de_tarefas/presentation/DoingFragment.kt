package com.albuquerque.app_de_tarefas.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.albuquerque.app_de_tarefas.databinding.DoingFragmentBinding

class DoingFragment : Fragment() {

    private var _binding: DoingFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DoingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}