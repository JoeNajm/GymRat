package com.example.finalgymlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.data.ExoInventoryViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentExoInventoryBinding

class ExoInventoryFragment : Fragment() {

    private var _binding: FragmentExoInventoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ExoInventoryViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExoInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this).get(ExoInventoryViewModel::class.java)

        viewModel.readAllExoInventory.observe(viewLifecycleOwner, {
            refreshExosUI(it)
        })

        binding.buttonUpperExos.setOnClickListener {
            binding.buttonUpperExos.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonLowerExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonAllExos.setBackgroundColor(resources.getColor(R.color.red))

            viewModel.readExoByType("Upper Body").observe(viewLifecycleOwner) {
                refreshExosUI(it)
            }
        }

        binding.buttonLowerExos.setOnClickListener {
            binding.buttonUpperExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonLowerExos.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonAllExos.setBackgroundColor(resources.getColor(R.color.red))

            viewModel.readExoByType("Legs").observe(viewLifecycleOwner) {
                refreshExosUI(it)
            }
        }

        binding.buttonAllExos.setOnClickListener {
            binding.buttonUpperExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonLowerExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonAllExos.setBackgroundColor(resources.getColor(R.color.green))

            viewModel.readAllExoInventory.observe(viewLifecycleOwner) {
                refreshExosUI(it)
            }
        }

        binding.buttonBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_exoInventoryFragment_to_sessionListFragment)
        }

        binding.floatingActionButtonExo.setOnClickListener {
            findNavController().navigate(R.id.action_exoInventoryFragment_to_addExoInventoryFragment)
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    fun onClick(exoinventory: ExoInventory){
        sharedViewModel.setCurrentExoInventory(exoinventory)
        findNavController().navigate(R.id.action_exoInventoryFragment_to_exoInventoryDetailsFragment)
    }

    private fun refreshExosUI(exoList: List<ExoInventory>) {
//        if(exoList.isEmpty()){
//            // TODO: If user deletes all data, then not initialize again ...
//            initializeExoInventory()
//        }
        // Passing the LayoutManager and Adapter to the RecyclerView of the Store
        val thisExoFragment = this
        binding.recyclerViewExos.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext, 2)
            adapter = ExoInventoryListAdapter(exoList, thisExoFragment, null)
        }
    }

    private fun initializeExoInventory(){
        val exo1 = ExoInventory(0, "Bench press", "drawable://"+R.drawable.bench, "Upper Body")
        exo1.let { viewModel.addExoInventory(it) }
        val exo10 = ExoInventory(0, "Inclined Bench press", "drawable://"+R.drawable.inclinebenchpresspng, "Upper Body")
        exo10.let { viewModel.addExoInventory(it) }
        val exo2 = ExoInventory(0, "Abs", "drawable://"+R.drawable.abs, "Upper Body")
        exo2.let { viewModel.addExoInventory(it) }
        val exo5 = ExoInventory(0, "Biceps curls", "drawable://"+R.drawable.dumbbell, "Upper Body")
        exo5.let { viewModel.addExoInventory(it) }
        val exo9 = ExoInventory(0, "Biceps hammers", "drawable://"+R.drawable.dumbbell, "Upper Body")
        exo9.let { viewModel.addExoInventory(it) }
        val exo7 = ExoInventory(0, "Cardio", "drawable://"+R.drawable.running, "Legs")
        exo7.let { viewModel.addExoInventory(it) }
        val exo8 = ExoInventory(0, "Triceps cord", "drawable://"+R.drawable.triceps, "Upper Body")
        exo8.let { viewModel.addExoInventory(it) }
        val exo11 = ExoInventory(0, "Leg press", "drawable://"+R.drawable.legpress, "Legs")
        exo11.let { viewModel.addExoInventory(it) }
        val exo12 = ExoInventory(0, "Leg extension", "drawable://"+R.drawable.legextension, "Legs")
        exo12.let { viewModel.addExoInventory(it) }
        val exo13 = ExoInventory(0, "Leg curl", "drawable://"+R.drawable.legcurl, "Legs")
        exo13.let { viewModel.addExoInventory(it) }
        val exo3 = ExoInventory(0, "Standing calves", "drawable://"+R.drawable.calves, "Legs")
        exo3.let { viewModel.addExoInventory(it) }
        val exo4 = ExoInventory(0, "Sitting calves", "drawable://"+R.drawable.calves, "Legs")
        exo4.let { viewModel.addExoInventory(it) }
        val exo17 = ExoInventory(0, "Squats", "drawable://"+R.drawable.squats, "Legs")
        exo17.let { viewModel.addExoInventory(it) }
        val exo18 = ExoInventory(0, "Bulgrian split squats", "drawable://"+R.drawable.bulgarian, "Legs")
        exo18.let { viewModel.addExoInventory(it) }
        val exo14 = ExoInventory(0, "Lat pulldown", "drawable://"+R.drawable.latpulldown, "Upper Body")
        exo14.let { viewModel.addExoInventory(it) }
        val exo15 = ExoInventory(0, "Seated row", "drawable://"+R.drawable.cablerow, "Upper Body")
        exo15.let { viewModel.addExoInventory(it) }
        val exo16 = ExoInventory(0, "Deadlift", "drawable://"+R.drawable.deadlift, "Upper Body")
        exo16.let { viewModel.addExoInventory(it) }
        val exo6 = ExoInventory(0, "Pull ups", "drawable://"+R.drawable.pullups, "Upper Body")
        exo6.let { viewModel.addExoInventory(it) }
        val exo19 = ExoInventory(0, "Shoulder press", "drawable://"+R.drawable.shoulderpress, "Upper Body")
        exo19.let { viewModel.addExoInventory(it) }

    }
}