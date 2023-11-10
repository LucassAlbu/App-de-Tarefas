package com.albuquerque.app_de_tarefas.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.albuquerque.app_de_tarefas.R
import com.albuquerque.app_de_tarefas.adapter.TaskAdapter
import com.albuquerque.app_de_tarefas.data.models.Status
import com.albuquerque.app_de_tarefas.data.models.Task
import com.albuquerque.app_de_tarefas.databinding.TodoFragmentBinding
import com.albuquerque.app_de_tarefas.util.gone
import com.albuquerque.app_de_tarefas.util.showBottomSheet
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class TodoFragment : Fragment() {

    private var _binding: TodoFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    private lateinit var reference: DatabaseReference

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TodoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        reference = Firebase.database.reference

        initClicks()
        initRecyclerView()
        getTasks()
    }

    private fun initClicks() {
        binding.fbNewTask.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToFormTaskFragment(null)
            findNavController().navigate(action)
        }
    }

    private fun initRecyclerView() {

        taskAdapter = TaskAdapter(requireContext()) { task, option ->
            optionSelected(task, option)

        }



        with(binding.rvTasks) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun optionSelected(task: Task, option: Int) {
        when (option) {
            TaskAdapter.SELECT_REMOVE -> {
                showBottomSheet(
                    titleDialog = R.string.text_title_dialog_delete,
                    titleButton = R.string.text_dialog_confirm,
                    message = getString(R.string.text_message_dialog_delete),
                    onClick = {
                        deleteTask(task)
                    }
                )
            }

            TaskAdapter.SELECT_EDIT -> {
             val action = HomeFragmentDirections
                 .actionHomeFragmentToFormTaskFragment(task)
                findNavController().navigate(action)

            }

            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(
                    requireContext(),
                    "Detalhes ${task.description} ",
                    Toast.LENGTH_SHORT
                ).show()

            }

            TaskAdapter.SELECT_NEXT -> {
                Toast.makeText(
                    requireContext(),
                    "Next ${task.description} ",
                    Toast.LENGTH_SHORT
                )
                    .show()

            }
        }
    }

    private fun getTasks() {
        reference
            .child("tasks")
            .child(auth.currentUser?.uid ?: "")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val taskList = mutableListOf<Task>()
                    for (ds in snapshot.children) {
                        val task = ds.getValue(Task::class.java) as Task
                        if (task.status == Status.TODO) {
                            taskList.add(task)
                        }
                    }
                    binding.loading.gone()
                    listEmpty(taskList)

                    taskList.reverse()
                    taskAdapter.submitList(taskList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

    private fun deleteTask(task: Task) {
        reference
            .child("tasks")
            .child(auth.currentUser?.uid ?: "")
            .child(task.id)
            .removeValue().addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        R.string.text_delete_task_success,
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun listEmpty(taskList: List<Task>) {
        binding.tvTextInfo.text = if (taskList.isEmpty()) {
            getString(R.string.text_task_list_empty)
        } else {
            ""
        }
    }

}