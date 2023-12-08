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
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.Food
import com.example.finalgymlog.data.FoodViewModel
import com.example.finalgymlog.data.FridgeFood
import com.example.finalgymlog.data.FridgeFoodViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentAddFoodFridgeBinding
import com.example.finalgymlog.databinding.FragmentExoInfoBinding
import com.example.finalgymlog.databinding.FragmentFridgeBinding
import kotlin.math.roundToInt

class AddFoodFridgeFragment : Fragment() {

    private var _binding: FragmentAddFoodFridgeBinding? = null
    private lateinit var mFridgeFoodViewModel: FridgeFoodViewModel
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddFoodFridgeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mFridgeFoodViewModel = ViewModelProvider(this).get(FridgeFoodViewModel::class.java)

        binding.addBtnFood.setOnClickListener {
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

            if(name != "" && protein != 0.0 && energy != 0.0){
                val food = FridgeFood(0, name, protein, energy, type)
                food?.let { mFridgeFoodViewModel.addFridgeFood(it) }
                findNavController().navigate(R.id.action_addFoodFridgeFragment_to_fridgeFragment)
            }else{
                Toast.makeText(requireContext(), "Please fill name, protein and calories.", Toast.LENGTH_LONG)
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