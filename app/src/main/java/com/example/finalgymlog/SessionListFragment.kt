package com.example.finalgymlog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        val sharedPreferences = requireActivity().getSharedPreferences("MyPreferenceGender", Context.MODE_PRIVATE)
        val gender = sharedPreferences.getString("gender", null)

        if(gender == "male"){
            binding.buttonMale.setVisibility(View.VISIBLE)
            binding.buttonFemale.setVisibility(View.GONE)
        }
        else if(gender == "female"){
            binding.buttonMale.setVisibility(View.GONE)
            binding.buttonFemale.setVisibility(View.VISIBLE)
        }

        viewModel.readAllSession.observe(viewLifecycleOwner, {
            refreshUsersUI(it)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_sessionListFragment_to_addSessionFragment)
        }

        binding.buttonGeneralInfo.setOnClickListener {
            findNavController().navigate(R.id.action_sessionListFragment_to_generalInfoFragment)
        }

        binding.buttonMale.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("gender", "female")
            editor.apply()
            binding.buttonMale.setVisibility(View.GONE)
            binding.buttonFemale.setVisibility(View.VISIBLE)
            Toast.makeText(requireContext(), "Successfully switched to female profile", Toast.LENGTH_LONG)
                .show()
        }

        binding.buttonFemale.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("gender", "male")
            editor.apply()
            binding.buttonFemale.setVisibility(View.GONE)
            binding.buttonMale.setVisibility(View.VISIBLE)
            Toast.makeText(requireContext(), "Successfully switched to male profile", Toast.LENGTH_LONG)
                .show()
        }

        binding.buttonStats.setOnClickListener {
            findNavController().navigate(R.id.action_sessionListFragment_to_statisticsFragment)
        }

        binding.buttonFridge.setOnClickListener {
            findNavController().navigate(R.id.action_sessionListFragment_to_fridgeFragment)
        }

        return root
    }

    fun onClick(session: Session) {
        sharedViewModel.setCurrentSession(session)
        val directions = SessionListFragmentDirections.actionSessionListFragmentToExoListFragment()
        view?.findNavController()?.navigate(directions)

    }

    private fun refreshUsersUI(sessionList: List<Session>) {
        if(sessionList.size == 0){
            binding.textAddSession.setVisibility(View.VISIBLE)
        }else{
            binding.textAddSession.setVisibility(View.GONE)
        }
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