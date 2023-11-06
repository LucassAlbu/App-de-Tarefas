package com.albuquerque.app_de_tarefas.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.albuquerque.app_de_tarefas.R
import com.albuquerque.app_de_tarefas.adapter.ViewPagerAdapter
import com.albuquerque.app_de_tarefas.databinding.HomeFragmentBinding
import com.albuquerque.app_de_tarefas.util.showBottomSheet
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initTabs()
        initClicks()
    }

    private fun initClicks() {
        binding.btnLogout.setOnClickListener {
            showBottomSheet(
                titleDialog = R.string.home_fragment_confirm_logout_title_dialog,
                titleButton = R.string.home_fragment_confirm_logout_button_dialog,
                message = getString(R.string.home_fragment_confirm_logout_message_dialog),
                onClick = {
                    auth.signOut()
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                }
            )
        }
    }

    private fun initTabs() {
        val pageAdapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = pageAdapter

        pageAdapter.addFragment(TodoFragment(), R.string.status_task_todo)
        pageAdapter.addFragment(DoingFragment(), R.string.status_task_doing)
        pageAdapter.addFragment(DoneFragment(), R.string.status_task_done)

        binding.viewPager.offscreenPageLimit = pageAdapter.itemCount

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(pageAdapter.getTitle(position))
        }.attach()
    }

}