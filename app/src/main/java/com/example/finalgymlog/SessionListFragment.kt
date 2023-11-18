package com.example.finalgymlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentSessionListBinding


class SessionListFragment : Fragment() {

    private val viewModel: SessionViewModel by activityViewModels()
    private val exoviewModel: ExoViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentSessionListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSessionListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.readAllSession.observe(viewLifecycleOwner, {
            refreshUsersUI(it)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_sessionListFragment_to_addSessionFragment)
        }

        binding.buttonGeneralInfo.setOnClickListener {
            findNavController().navigate(R.id.action_sessionListFragment_to_generalInfoFragment)
        }

        return root
    }

    fun onClick(session: Session) {
        sharedViewModel.setCurrentSession(session)
        val directions = SessionListFragmentDirections.actionSessionListFragmentToExoListFragment()
        view?.findNavController()?.navigate(directions)

    }

    private fun refreshUsersUI(sessionList: List<Session>) {
        // Passing the LayoutManager and Adapter to the RecyclerView of the Store
        val thisSessionFragment = this
        binding.recyclerViewUser.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext, 2)
            adapter = SessionListAdapter(sessionList, thisSessionFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}