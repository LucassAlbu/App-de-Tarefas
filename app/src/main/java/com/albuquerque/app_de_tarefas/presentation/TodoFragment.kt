package com.albuquerque.app_de_tarefas.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.albuquerque.app_de_tarefas.R
import com.albuquerque.app_de_tarefas.adapter.TaskAdapter
import com.albuquerque.app_de_tarefas.adapter.TaskTopAdapter
import com.albuquerque.app_de_tarefas.data.models.Status
import com.albuquerque.app_de_tarefas.data.models.Task
import com.albuquerque.app_de_tarefas.databinding.TodoFragmentBinding

class TodoFragment : Fragment() {

    private var _binding: TodoFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskTopAdapter: TaskTopAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TodoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()
        initRecyclerView()
        getTasks()
    }

    private fun initClicks() {
        binding.fbNewTask.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment) }
    }

    private fun initRecyclerView() {
        taskTopAdapter = TaskTopAdapter() { task, option ->
            optionSelected(task, option)

        }
        taskAdapter = TaskAdapter(requireContext()) { task, option ->
            optionSelected(task, option)

        }

        val concatAdapter = ConcatAdapter(taskTopAdapter, taskAdapter)

        with(binding.rvTasks) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = concatAdapter
        }
    }

    private fun optionSelected(task: Task, option: Int) {
        when (option) {
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
        val taskTopList = listOf(
            Task("0", "Tarefa no topo", Status.TODO),
            Task("1", "Concat Adapter", Status.TODO),
        )
        val taskList = listOf(
            Task("0", "Criar uma nova tela pro App", Status.TODO),
            Task("1", "Validar navegação", Status.TODO),
            Task("2", "Adicionar animação", Status.TODO),
            Task("3", "Salvar dados localmente", Status.TODO),
            Task("4", "Configurar logout do app", Status.TODO),
            Task("5", "Alterar cor da tela de login", Status.TODO),
        )
        taskTopAdapter.submitList(taskTopList)
        taskAdapter.submitList(taskList)
    }

}