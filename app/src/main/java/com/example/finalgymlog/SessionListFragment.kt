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
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.data.ExoInventoryViewModel
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentSessionListBinding


class SessionListFragment : Fragment() {

    private val viewModel: SessionViewModel by activityViewModels()
    private val exoInventoryViewModel: ExoInventoryViewModel by activityViewModels()
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
        exoInventoryViewModel.readAllExoInventory.observe(viewLifecycleOwner, {
            if(it.isEmpty()){
                // TODO: If user deletes all data, then not initialize again ...
                initializeExoInventory()
            }
        })

        binding.buttonStats.setOnClickListener {
            findNavController().navigate(R.id.action_sessionListFragment_to_statisticsFragment)
        }

        binding.buttonFridge.setOnClickListener {
            findNavController().navigate(R.id.action_sessionListFragment_to_fridgeFragment)
        }

        binding.buttonAddExo.setOnClickListener {
            findNavController().navigate(R.id.action_sessionListFragment_to_exoInventoryFragment)
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

    private fun initializeExoInventory(){
        val exo1 = ExoInventory(0, "Bench press", "drawable://"+R.drawable.bench, "Chest")
        exo1.let { exoInventoryViewModel.addExoInventory(it) }
        val exo10 = ExoInventory(0, "Inclined Bench press", "drawable://"+R.drawable.inclinebenchpresspng, "Chest")
        exo10.let { exoInventoryViewModel.addExoInventory(it) }
        val exo2 = ExoInventory(0, "Situps", "drawable://"+R.drawable.situps, "Core")
        exo2.let { exoInventoryViewModel.addExoInventory(it) }
        val exo5 = ExoInventory(0, "Biceps curls", "drawable://"+R.drawable.dumbbell, "Arms")
        exo5.let { exoInventoryViewModel.addExoInventory(it) }
        val exo9 = ExoInventory(0, "Biceps hammers", "drawable://"+R.drawable.dumbbell, "Arms")
        exo9.let { exoInventoryViewModel.addExoInventory(it) }
        val exo7 = ExoInventory(0, "Running", "drawable://"+R.drawable.running, "Cardio")
        exo7.let { exoInventoryViewModel.addExoInventory(it) }
        val exo8 = ExoInventory(0, "Triceps cord", "drawable://"+R.drawable.triceps, "Arms")
        exo8.let { exoInventoryViewModel.addExoInventory(it) }
        val exo11 = ExoInventory(0, "Leg press", "drawable://"+R.drawable.legpress, "Legs")
        exo11.let { exoInventoryViewModel.addExoInventory(it) }
        val exo12 = ExoInventory(0, "Leg extension", "drawable://"+R.drawable.legextension, "Legs")
        exo12.let { exoInventoryViewModel.addExoInventory(it) }
        val exo13 = ExoInventory(0, "Leg curl", "drawable://"+R.drawable.legcurl, "Legs")
        exo13.let { exoInventoryViewModel.addExoInventory(it) }
        val exo3 = ExoInventory(0, "Standing calves", "drawable://"+R.drawable.calves, "Legs")
        exo3.let { exoInventoryViewModel.addExoInventory(it) }
        val exo4 = ExoInventory(0, "Sitting calves", "drawable://"+R.drawable.calves, "Legs")
        exo4.let { exoInventoryViewModel.addExoInventory(it) }
        val exo17 = ExoInventory(0, "Squats", "drawable://"+R.drawable.squats, "Legs")
        exo17.let { exoInventoryViewModel.addExoInventory(it) }
        val exo18 = ExoInventory(0, "Bulgrian split squats", "drawable://"+R.drawable.bulgarian, "Legs")
        exo18.let { exoInventoryViewModel.addExoInventory(it) }
        val exo14 = ExoInventory(0, "Lat pulldown", "drawable://"+R.drawable.latpulldown, "Back")
        exo14.let { exoInventoryViewModel.addExoInventory(it) }
        val exo15 = ExoInventory(0, "Seated row", "drawable://"+R.drawable.cablerow, "Back")
        exo15.let { exoInventoryViewModel.addExoInventory(it) }
        val exo16 = ExoInventory(0, "Deadlift", "drawable://"+R.drawable.deadlift, "Back")
        exo16.let { exoInventoryViewModel.addExoInventory(it) }
        val exo6 = ExoInventory(0, "Pull ups", "drawable://"+R.drawable.pullups, "Back")
        exo6.let { exoInventoryViewModel.addExoInventory(it) }
        val exo19 = ExoInventory(0, "Shoulder press", "drawable://"+R.drawable.shoulderpress, "Shoulders")
        exo19.let { exoInventoryViewModel.addExoInventory(it) }

    }

}