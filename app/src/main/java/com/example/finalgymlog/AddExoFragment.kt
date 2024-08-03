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
        val name_of_session = currentSession?.name

        if ("arms" in name_of_session?.toLowerCase()!!) {
            setAllButtonsRed()
            binding.buttonArms.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Arms").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        } else if ("back" in name_of_session?.toLowerCase()!!) {
            setAllButtonsRed()
            binding.buttonBack.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Back").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        } else if ("chest" in name_of_session?.toLowerCase()!!) {
            setAllButtonsRed()
            binding.buttonChest.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Chest").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        } else if ("legs" in name_of_session?.toLowerCase()!!) {
            setAllButtonsRed()
            binding.buttonLower.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Legs").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        } else if ("cardio" in name_of_session?.toLowerCase()!!) {
            setAllButtonsRed()
            binding.buttonCardio.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Cardio").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        } else if ("core" in name_of_session?.toLowerCase()!!) {
            setAllButtonsRed()
            binding.buttonCore.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Core").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }
            else{
            mExoInventoryViewModel.readAllExoInventory.observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }




        binding.buttonNewExo.setOnClickListener {
            STATE = "new"
            binding.buttonNewExo.backgroundTintList = resources.getColorStateList(R.color.green)
            binding.buttonExistingExo.backgroundTintList = resources.getColorStateList(R.color.red)
            binding.linearLayoutExoType.visibility = View.GONE
            binding.textExoStatus.setText("New Exo")
            display(STATE, size_of_inventory)
        }
        binding.buttonExistingExo.setOnClickListener {
            STATE = "inventory"
            binding.buttonNewExo.backgroundTintList = resources.getColorStateList(R.color.red)
            binding.buttonExistingExo.backgroundTintList = resources.getColorStateList(R.color.green)
            binding.linearLayoutExoType.visibility = View.VISIBLE
            binding.textExoStatus.setText("Existing Exercises")
            display(STATE, size_of_inventory)
        }

        binding.buttonChest.setOnClickListener {
            setAllButtonsRed()
            binding.buttonChest.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Chest").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }

        binding.buttonBack.setOnClickListener {
            setAllButtonsRed()
            binding.buttonBack.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Back").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }

        binding.buttonShoulders.setOnClickListener {
            setAllButtonsRed()
            binding.buttonShoulders.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Shoulders").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }

        binding.buttonCardio.setOnClickListener {
            setAllButtonsRed()
            binding.buttonCardio.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Cardio").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }

        binding.buttonArms.setOnClickListener {
            setAllButtonsRed()
            binding.buttonArms.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Arms").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }

        binding.buttonCore.setOnClickListener {
            setAllButtonsRed()
            binding.buttonCore.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Core").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }

        binding.buttonLower.setOnClickListener {
            setAllButtonsRed()
            binding.buttonLower.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Legs").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }

        binding.buttonOther.setOnClickListener {
            setAllButtonsRed()
            binding.buttonOther.backgroundTintList = resources.getColorStateList(R.color.green)
            mExoInventoryViewModel.readExoByType("Other").observe(viewLifecycleOwner) {
                refreshInventoryUI(it)
                size_of_inventory = it.size
                display(STATE, size_of_inventory)
            }
        }

        binding.buttonAll.setOnClickListener {
            setAllButtonsRed()
            binding.buttonAll.backgroundTintList = resources.getColorStateList(R.color.green)
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

    private fun setAllButtonsRed(){
        binding.buttonChest.backgroundTintList = resources.getColorStateList(R.color.red)
        binding.buttonBack.backgroundTintList = resources.getColorStateList(R.color.red)
        binding.buttonShoulders.backgroundTintList = resources.getColorStateList(R.color.red)
        binding.buttonCardio.backgroundTintList = resources.getColorStateList(R.color.red)
        binding.buttonArms.backgroundTintList = resources.getColorStateList(R.color.red)
        binding.buttonCore.backgroundTintList = resources.getColorStateList(R.color.red)
        binding.buttonLower.backgroundTintList = resources.getColorStateList(R.color.red)
        binding.buttonOther.backgroundTintList = resources.getColorStateList(R.color.red)
        binding.buttonAll.backgroundTintList = resources.getColorStateList(R.color.red)
    }
}