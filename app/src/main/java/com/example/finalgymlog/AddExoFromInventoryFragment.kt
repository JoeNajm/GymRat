package com.example.finalgymlog

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoInventoryViewModel
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.Food
import com.example.finalgymlog.data.FoodViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentAddExoFromInventoryBinding
import com.example.finalgymlog.databinding.FragmentAddFoodFromFridgeBinding
import java.io.File


class AddExoFromInventoryFragment : Fragment() {

    private var _binding: FragmentAddExoFromInventoryBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val mExoViewModel: ExoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddExoFromInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val currentExo = sharedViewModel.getCurrentExoInventory().value

        binding.exoName.setText(currentExo!!.name)

        val PathOfImage = currentExo.imagepath

        if(PathOfImage!!.contains("drawable", ignoreCase = true)){
            val intId = PathOfImage.substring(11, PathOfImage.length).toInt()
            binding.exoImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), intId))
        } else{
            val file = File(requireContext().filesDir, PathOfImage)
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            binding.exoImage.setImageBitmap(bitmap)
        }

        binding.btnExo.setOnClickListener {
            if(binding.exoName.text.toString() != ""){
                val session = sharedViewModel.getCurrentSession().value
                val name = binding.exoName.text.toString()
                val reps = binding.exoReps.text.toString()
                val weights = binding.exoWeights.text.toString()
                val comment = binding.exoComments.text.toString()

                val exo = session?.id?.let { Exo(0, name, reps, weights, comment, it) }
                // Add Data to Database
                exo?.let { mExoViewModel.addExo(it) }
                Toast.makeText(requireContext(), "Don't forget why you started!", Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.action_addExoFromInventoryFragment_to_exoListFragment)

            } else{
                Toast.makeText(requireContext(), "Please fill out the name", Toast.LENGTH_LONG)
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