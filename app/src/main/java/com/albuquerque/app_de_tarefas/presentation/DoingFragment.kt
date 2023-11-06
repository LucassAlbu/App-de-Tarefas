package com.albuquerque.app_de_tarefas.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.albuquerque.app_de_tarefas.adapter.TaskAdapter
import com.albuquerque.app_de_tarefas.data.models.Status
import com.albuquerque.app_de_tarefas.data.models.Task
import com.albuquerque.app_de_tarefas.databinding.DoingFragmentBinding

class DoingFragment : Fragment() {

    private var _binding: DoingFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DoingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getTasks()
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

            TaskAdapter.SELECT_BACK -> {
                Toast.makeText(
                    requireContext(),
                    "Back ${task.description} ",
                    Toast.LENGTH_SHORT
                )
                    .show()

            }

            TaskAdapter.SELECT_REMOVE -> {
                Toast.makeText(
                    requireContext(),
                    "Removendo ${task.description} ",
                    Toast.LENGTH_SHORT
                ).show()
            }

            TaskAdapter.SELECT_EDIT -> {
                Toast.makeText(
                    requireContext(),
                    "Editando ${task.description} ",
                    Toast.LENGTH_SHORT
                ).show()

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
        val taskList = listOf(
            Task("0", "Vaidar informações", Status.DOING),
            Task("1", "Criar novo app", Status.DOING)
        )
        taskAdapter.submitList(taskList)
    }

}