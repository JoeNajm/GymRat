package com.example.finalgymlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.FridgeFood
import com.example.finalgymlog.data.FridgeFoodViewModel
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentFridgeFoodInfoBinding
import com.example.finalgymlog.databinding.FragmentSessionInfoBinding

class FridgeFoodInfoFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: FridgeFoodViewModel by activityViewModels()
    private var _binding: FragmentFridgeFoodInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFridgeFoodInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val currentFood = sharedViewModel.getCurrentFridgeFood().value
        binding.foodName.setText(currentFood?.name)
        binding.proteins.setText(currentFood?.protein.toString())
        binding.energy.setText(currentFood?.energy.toString())
        binding.type.setText("Type : " + currentFood?.type.toString())

        binding.buttonBackArrow.setOnClickListener{
            findNavController().navigate(R.id.action_fridgeFoodInfoFragment_to_fridgeFragment)
        }

        binding.buttonBackFood.setOnClickListener {

            val proteins = binding.proteins.text.toString().toDouble()
            val energy = binding.energy.text.toString().toDouble()
            val type = binding.type.text.toString()
            val updatedFridgeFood = FridgeFood(
                currentFood!!.id,
                binding.foodName.text.toString(),
                proteins,
                energy,
                type
            )
            viewModel.updateFridgeFood(updatedFridgeFood)
            sharedViewModel.setCurrentFridgeFood(updatedFridgeFood)

            findNavController().navigate(R.id.action_fridgeFoodInfoFragment_to_fridgeFragment)
        }

        binding.buttonDeleteTrash.setOnClickListener {
            binding.buttonDeleteTrash.setVisibility(View.GONE)
            binding.buttonDeleteSure.setVisibility(View.VISIBLE)
        }

        binding.buttonDeleteSure.setOnClickListener {

            if (currentFood != null) {
                viewModel.deleteFridgeFood(currentFood)
            }

            binding.buttonDeleteTrash.setVisibility(View.VISIBLE)
            binding.buttonDeleteSure.setVisibility(View.GONE)
            findNavController().navigate(R.id.action_sessionInfoFragment_to_sessionListFragment)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}