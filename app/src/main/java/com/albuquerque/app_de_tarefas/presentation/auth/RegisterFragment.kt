package com.albuquerque.app_de_tarefas.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.albuquerque.app_de_tarefas.R
import com.albuquerque.app_de_tarefas.databinding.RegisterFragmentBinding
import com.albuquerque.app_de_tarefas.util.initToolbar
import com.albuquerque.app_de_tarefas.util.showBottomSheet


class RegisterFragment : Fragment() {

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initClicks()
    }

    private fun initClicks() {
        binding.btnCreateAccount.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                Toast.makeText(requireContext(), "Deu Brasil", Toast.LENGTH_SHORT).show()
            } else {
                showBottomSheet(message = R.string.empty_password)
            }
        } else {
            showBottomSheet(message = R.string.register_firebase_tv_invalid_email)
        }
    }
}