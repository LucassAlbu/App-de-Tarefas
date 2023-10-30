package com.albuquerque.app_de_tarefas.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.albuquerque.app_de_tarefas.R
import com.albuquerque.app_de_tarefas.databinding.FormTaskFragmentBinding
import com.albuquerque.app_de_tarefas.util.initToolbar
import com.albuquerque.app_de_tarefas.util.showBottomSheet

class FormTaskFragment : Fragment() {


    private var _binding: FormTaskFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FormTaskFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initClicks()
    }

    private fun initClicks() {
        binding.btnSave.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val description = binding.edtDescription.text.toString().trim()
        if (description.isNotEmpty()) {

        } else {
            showBottomSheet(message = getString(R.string.form_task_empty_description))

        }
    }

}