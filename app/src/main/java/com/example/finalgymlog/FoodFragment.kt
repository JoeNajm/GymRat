package com.example.finalgymlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentExoInfoBinding
import com.example.finalgymlog.databinding.FragmentFoodBinding

class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.buttonBackFood.setOnClickListener{
            findNavController().navigate(R.id.action_foodFragment_to_exoListFragment)
        }

        binding.floatingActionButtonExo.setOnClickListener{
            findNavController().navigate(R.id.action_foodFragment_to_addFoodFragment)
        }


        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

}