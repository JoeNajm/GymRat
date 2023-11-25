package com.example.finalgymlog

import android.content.Context
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
import com.example.finalgymlog.data.Food
import com.example.finalgymlog.data.FoodViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentExoInfoBinding
import com.example.finalgymlog.databinding.FragmentFoodBinding

class FoodFragment : Fragment() {

    private val viewModel: FoodViewModel by activityViewModels()
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

        val sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val sessionID = sharedViewModel.getCurrentSession().value?.id
        var totalProtein = 0.0
        var totalEnergy = 0.0
        var targetProteins = sharedPreferences.getString("proteinTarget", 155.0f.toString())?.toDouble()

        binding.proteinTarget.setText(targetProteins.toString())


        sessionID?.let {
            viewModel.readFoodByParentId(it).observe(viewLifecycleOwner, {
                refreshFoodUI(it)
                it.forEach {
                    totalProtein += it.protein
                    totalEnergy += it.energy
                }
                binding.proteinProgessText.text = totalProtein.toString() + " / " + targetProteins.toString() + " g"
                binding.proteinProgess.progress = (totalProtein / targetProteins!! * 100).toInt()

                binding.textTotalProteins.text = "Total Proteins: " + totalProtein.toString() + " g"
                binding.textTotalEnergy.text = "Total Energy: " + totalEnergy.toString() + " kcal"
            })
        }

        binding.buttonBackFood.setOnClickListener{
            findNavController().navigate(R.id.action_foodFragment_to_exoListFragment)
        }

        binding.buttonSaveProteinTarget.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("proteinTarget", binding.proteinTarget.text.toString())
            editor.apply()
            targetProteins = binding.proteinTarget.text.toString().toDouble()
            binding.proteinProgessText.text = totalProtein.toString() + " / " + targetProteins.toString() + " g"
            binding.proteinProgess.progress = (totalProtein / targetProteins!! * 100).toInt()
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

    private fun refreshFoodUI(foodList: List<Food>) {
        // Passing the LayoutManager and Adapter to the RecyclerView of the Store
        val thisFoodFragment = this
        binding.recyclerViewFood.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext, 2)
            adapter = FoodListAdapter(foodList, thisFoodFragment)
        }
    }

}