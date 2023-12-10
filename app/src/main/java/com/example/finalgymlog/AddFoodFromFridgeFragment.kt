package com.example.finalgymlog

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
import com.example.finalgymlog.data.Food
import com.example.finalgymlog.data.FoodViewModel
import com.example.finalgymlog.data.FridgeFood
import com.example.finalgymlog.data.FridgeFoodViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentAddFoodBinding
import com.example.finalgymlog.databinding.FragmentAddFoodFromFridgeBinding
import kotlin.math.roundToInt

class AddFoodFromFridgeFragment : Fragment() {

    private var _binding: FragmentAddFoodFromFridgeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var mFoodViewModel: FoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddFoodFromFridgeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mFoodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        val currentFridgeFood = sharedViewModel.getCurrentFridgeFood().value
        val currentSession = sharedViewModel.getCurrentSession().value

        binding.foodName.text = currentFridgeFood!!.name
        binding.foodProteins.text = "Proteins (per 100 g) : " + currentFridgeFood!!.protein.toString() + " g"
        binding.foodEnergy.text = "Energy (per 100 g) : " + currentFridgeFood!!.energy.toString() + " kcal"
        binding.foodType.text = "Type : " + currentFridgeFood!!.type

        binding.addBtnFood.setOnClickListener {
            if(binding.quantityText.text.toString() != ""){
                val quantity = binding.quantityText.text.toString().toDouble()
                val new_protein = currentFridgeFood.protein.toString().toDouble() * quantity / 100
                val new_energy = currentFridgeFood.energy.toString().toDouble() * quantity / 100

                val food = currentSession?.id?.let { Food(0, currentFridgeFood.name, new_protein, new_energy, currentFridgeFood.type, it) }
                // Add Data to Database
                food?.let { mFoodViewModel.addFood(it) }
                Toast.makeText(requireContext(), "YUM YUM!", Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.action_addFoodFromFridgeFragment_to_foodFragment)

            } else{
                Toast.makeText(requireContext(), "Please fill out the quantity", Toast.LENGTH_LONG)
                    .show()
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }


}