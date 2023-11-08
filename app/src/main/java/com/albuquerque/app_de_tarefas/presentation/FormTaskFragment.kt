package com.albuquerque.app_de_tarefas.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.albuquerque.app_de_tarefas.R
import com.albuquerque.app_de_tarefas.data.models.Status
import com.albuquerque.app_de_tarefas.data.models.Task
import com.albuquerque.app_de_tarefas.databinding.FormTaskFragmentBinding
import com.albuquerque.app_de_tarefas.util.gone
import com.albuquerque.app_de_tarefas.util.initToolbar
import com.albuquerque.app_de_tarefas.util.showBottomSheet
import com.albuquerque.app_de_tarefas.util.visible
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class FormTaskFragment : Fragment() {


    private var _binding: FormTaskFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task

    private var status: Status = Status.TODO

    private var newTask: Boolean = true

    private lateinit var reference: DatabaseReference

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FormTaskFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        auth = Firebase.auth
        reference = Firebase.database.reference

        initClicks()
    }

    private fun initClicks() {
        binding.btnSave.setOnClickListener {
            validateData()
        }
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            status = when (id) {
                R.id.rb_to_do -> Status.TODO
                R.id.rb_doing -> Status.DOING
                else -> Status.DONE
            }

        }
    }

    private fun validateData() {
        val description = binding.edtDescription.text.toString().trim()
        if (description.isNotEmpty()) {

            binding.loading.visible()
            if (newTask) task = Task()
            task.id = reference.database.reference.push().key ?: ""
            task.description = description
            task.status = status
            saveTask()
        } else {
            showBottomSheet(message = getString(R.string.form_task_empty_description))

        }
    }

    private fun saveTask() {
        reference
            .child("tasks")
            .child(auth.currentUser?.uid ?: "")
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        R.string.form_task_save_success,
                        Toast.LENGTH_SHORT
                    ).show()
                    if (newTask){
                        findNavController().popBackStack()
                    }else{
                        binding.loading.gone()
                    }

                } else {
                    binding.loading.gone()
                    showBottomSheet(message = getString(R.string.error_generic))
                }
            }
    }
}