package com.example.finalgymlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.FoodViewModel
import com.example.finalgymlog.data.FridgeFood
import com.example.finalgymlog.data.FridgeFoodViewModel
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentExoInfoBinding
import com.example.finalgymlog.databinding.FragmentFridgeBinding

class FridgeFragment : Fragment() {

    private var _binding: FragmentFridgeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FridgeFoodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFridgeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.readAllFridgeFood.observe(viewLifecycleOwner, {
            refreshFridgeUI(it)
        })

        binding.buttonBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_fridgeFragment_to_sessionListFragment)
        }

        binding.floatingActionButtonExo.setOnClickListener {
            findNavController().navigate(R.id.action_fridgeFragment_to_addFoodFridgeFragment)
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun refreshFridgeUI(fridgeList: List<FridgeFood>) {
        if(fridgeList.size == 0){
            binding.textAddFridgeFood.setVisibility(View.VISIBLE)
        }else{
            binding.textAddFridgeFood.setVisibility(View.GONE)
        }
        // Passing the LayoutManager and Adapter to the RecyclerView of the Store
        val thisFridgeFragment = this
        binding.recyclerViewFood.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext, 2)
            adapter = FridgeFoodListAdapter(fridgeList, thisFridgeFragment)
        }
    }
}