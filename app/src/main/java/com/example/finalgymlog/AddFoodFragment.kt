package com.example.finalgymlog

import android.health.connect.datatypes.units.Energy
import android.os.Bundle
import android.text.TextUtils
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
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.Food
import com.example.finalgymlog.data.FoodViewModel
import com.example.finalgymlog.data.FridgeFood
import com.example.finalgymlog.data.FridgeFoodViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentAddFoodBinding
import com.example.finalgymlog.databinding.FragmentFoodBinding
import kotlin.math.roundToInt

class AddFoodFragment : Fragment() {
    private lateinit var mFoodViewModel: FoodViewModel
    private val mFridgeviewModel: FridgeFoodViewModel by activityViewModels()
    private var _binding: FragmentAddFoodBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddFoodBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mFoodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        var STATE = "fridge"
        var size_of_fridge = 0

        mFridgeviewModel.readAllFridgeFood.observe(viewLifecycleOwner) {
            refreshFridgeUI(it)
            size_of_fridge = it.size
            display(STATE, size_of_fridge)
        }

        binding.buttonNewFood.setOnClickListener {
            STATE = "new"
            display(STATE, size_of_fridge)
        }
        binding.buttonExistingFood.setOnClickListener {
            STATE = "fridge"
            display(STATE, size_of_fridge)
        }

        binding.addBtnFood.setOnClickListener{
            insertDataToDatabase()
        }




        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun refreshFridgeUI(fridgeList: List<FridgeFood>) {

        val thisFoodFragment = this

        binding.recyclerViewFood.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext, 2)
//            adapter = FridgeFoodListAdapter(fridgeList)
            adapter = FridgeFoodListAdapter(fridgeList, null, thisFoodFragment)
        }
    }

    private fun display(state: String, size_fridge: Int){

        if(state == "fridge"){
            if(size_fridge == 0){
                binding.emtpyFridgeMessage.visibility = View.VISIBLE
            }else{
                binding.emtpyFridgeMessage.visibility = View.GONE
            }
            binding.linearLayoutNewFood.visibility = View.GONE
            binding.addBtnFood.visibility = View.GONE
            binding.recyclerViewFood.visibility = View.VISIBLE
        } else if(state == "new"){
            binding.emtpyFridgeMessage.visibility = View.GONE
            binding.linearLayoutNewFood.visibility = View.VISIBLE
            binding.addBtnFood.visibility = View.VISIBLE
            binding.recyclerViewFood.visibility = View.GONE
        }
    }

    private fun insertDataToDatabase() {

        val session = sharedViewModel.getCurrentSession().value
        var protein = 0.0
        var energy = 0.0
        if (!binding.addFoodProteins.text.toString().isEmpty()){
            protein = (binding.addFoodProteins.text.toString().toDouble() * 100.0).roundToInt() / 100.0
        }
        if (!binding.addFoodEnergy.text.toString().isEmpty()){
            energy = (binding.addFoodEnergy.text.toString().toDouble() * 100.0).roundToInt() / 100.0
        }

        val name = binding.addFoodName.text.toString()
        val type = binding.addFoodType.text.toString()

        if (inputCheck(name)) {
            val food = session?.id?.let { Food(0, name, protein, energy, type, it) }
            // Add Data to Database
            food?.let { mFoodViewModel.addFood(it) }
            Toast.makeText(requireContext(), "The Sky is your limit!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigate(R.id.action_addFoodFragment_to_foodFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }

    fun onClick(food: FridgeFood){
        sharedViewModel.setCurrentFridgeFood(food)
        findNavController().navigate(R.id.action_addFoodFragment_to_addFoodFromFridgeFragment)
    }

}