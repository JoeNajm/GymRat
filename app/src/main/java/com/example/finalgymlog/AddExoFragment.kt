package com.example.finalgymlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.data.ExoInventoryViewModel
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentAddExoBinding


class AddExoFragment : Fragment() {
    private lateinit var mExoViewModel: ExoViewModel
    private val mExoInventoryViewModel: ExoInventoryViewModel by activityViewModels()
    private var _binding: FragmentAddExoBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddExoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mExoViewModel = ViewModelProvider(this).get(ExoViewModel::class.java)

        val currentSession = sharedViewModel.getCurrentSession().value

        var STATE = "inventory"
        var size_of_inventory = 0

        if("upper" in currentSession!!.name.lowercase()){
            binding.buttonUpperExos.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonLowerExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonAllExos.setBackgroundColor(resources.getColor(R.color.red))

            mExoInventoryViewModel.readExoByType("Upper Body").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        } else if("leg" in currentSession.name.lowercase()){
            binding.buttonUpperExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonLowerExos.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonAllExos.setBackgroundColor(resources.getColor(R.color.red))

            mExoInventoryViewModel.readExoByType("Legs").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        } else{
            binding.buttonUpperExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonLowerExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonAllExos.setBackgroundColor(resources.getColor(R.color.green))

            mExoInventoryViewModel.readAllExoInventory.observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }


        binding.buttonNewExo.setOnClickListener {
            STATE = "new"
            binding.buttonNewExo.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonExistingExo.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonLowerExos.visibility = View.GONE
            binding.buttonUpperExos.visibility = View.GONE
            binding.buttonAllExos.visibility = View.GONE
            binding.textExoStatus.setText("New Exo")
            display(STATE, size_of_inventory)
        }
        binding.buttonExistingExo.setOnClickListener {
            STATE = "inventory"
            binding.buttonNewExo.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonExistingExo.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonLowerExos.visibility = View.VISIBLE
            binding.buttonUpperExos.visibility = View.VISIBLE
            binding.buttonAllExos.visibility = View.VISIBLE
            binding.textExoStatus.setText("Existing Exercises")
            display(STATE, size_of_inventory)
        }

        binding.buttonUpperExos.setOnClickListener {
            binding.buttonUpperExos.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonLowerExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonAllExos.setBackgroundColor(resources.getColor(R.color.red))

            mExoInventoryViewModel.readExoByType("Upper Body").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }

        binding.buttonLowerExos.setOnClickListener {
            binding.buttonUpperExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonLowerExos.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonAllExos.setBackgroundColor(resources.getColor(R.color.red))

            mExoInventoryViewModel.readExoByType("Legs").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }

        binding.buttonAllExos.setOnClickListener {
            binding.buttonUpperExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonLowerExos.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonAllExos.setBackgroundColor(resources.getColor(R.color.green))

            mExoInventoryViewModel.readAllExoInventory.observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }


        binding.addBtnExo.setOnClickListener {
            insertDataToDatabase()
        }

        return root
    }

    private fun insertDataToDatabase() {

        val name = binding.addExoName.text.toString()
        val reps = binding.addExoReps.text.toString()
        val comment = binding.addExoComments.text.toString()
        val weights = binding.addExoWeights.text.toString()
        val session = sharedViewModel.getCurrentSession().value

        if (name != "") {
            val exo = session?.id?.let { Exo(0, name, reps, weights, comment, it) }
            // Add Data to Database
            exo?.let { mExoViewModel.addExo(it) }
            Toast.makeText(requireContext(), "Keep Pushing!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigate(R.id.action_addExoFragment_to_exoListFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all name.", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun display(state: String, size_inventory: Int){

        if(state == "inventory"){
            if(size_inventory == 0){
                binding.emptyInventoryMessage.visibility = View.VISIBLE
            }else{
                binding.emptyInventoryMessage.visibility = View.GONE
            }
            binding.linearLayoutNewExo.visibility = View.GONE
            binding.addBtnExo.visibility = View.GONE
            binding.recyclerViewExo.visibility = View.VISIBLE
        } else if(state == "new"){
            binding.emptyInventoryMessage.visibility = View.GONE
            binding.linearLayoutNewExo.visibility = View.VISIBLE
            binding.addBtnExo.visibility = View.VISIBLE
            binding.recyclerViewExo.visibility = View.GONE
        }
    }

    private fun refreshInventoryUI(inventoryList: List<ExoInventory>) {

        val thisExoFragment = this

        binding.recyclerViewExo.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext, 2)
            adapter = ExoInventoryListAdapter(inventoryList, null, thisExoFragment)
        }
    }

    fun onClick(exoinventory: ExoInventory){
        sharedViewModel.setCurrentExoInventory(exoinventory)
        findNavController().navigate(R.id.action_addExoFragment_to_addExoFromInventoryFragment)
    }
}