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
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentExoInfoBinding
import com.example.finalgymlog.databinding.FragmentExoListBinding
import com.example.finalgymlog.databinding.FragmentSessionListBinding


class ExoInfoFragment : Fragment() {

    private val exoViewModel: ExoViewModel by activityViewModels()
    private var _binding: FragmentExoInfoBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExoInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val currentExo = sharedViewModel.getCurrentExo().value

        binding.exoName.setText(currentExo?.name)
        binding.exoComments.setText(currentExo?.comment)
        binding.exoReps.setText(currentExo?.reps)
        binding.exoWeights.setText(currentExo?.weights)

        if("abs" in currentExo?.name!!.lowercase()){
            binding.exoImage.setImageResource(R.drawable.abs)
        }
        else if("bench" in currentExo.name.lowercase()){
            binding.exoImage.setImageResource(R.drawable.bench)
        }
        else if("calves" in currentExo.name.lowercase()){
            binding.exoImage.setImageResource(R.drawable.calves)
        }
        else if("running" in currentExo.name.lowercase()){
            binding.exoImage.setImageResource(R.drawable.running)
        }
        else if("cardio" in currentExo.name.lowercase()){
            binding.exoImage.setImageResource(R.drawable.running)
        }
        else if("pull up" in currentExo.name.lowercase()){
            binding.exoImage.setImageResource(R.drawable.pullups)
        }
        else if("tricep" in currentExo.name.lowercase()){
            binding.exoImage.setImageResource(R.drawable.triceps)
        }
        else if("bicep" in currentExo.name.lowercase()){
            binding.exoImage.setImageResource(R.drawable.dumbbell)
        }
        else {
            binding.exoImage.setImageResource(R.drawable.rat)
        }

        binding.buttonBackExo.setOnClickListener{
            findNavController().navigate(R.id.action_exoInfoFragment_to_exoListFragment)
        }

        binding.buttonBackExo.setOnClickListener {
            val updatedExo = Exo(
                currentExo?.id!!,
                binding.exoName.text.toString(),
                binding.exoReps.text.toString(),
                binding.exoWeights.text.toString(),
                binding.exoComments.text.toString(),
                currentExo.parentId
            )
            exoViewModel.updateExo(updatedExo)
            findNavController().navigate(R.id.action_exoInfoFragment_to_exoListFragment)
        }

        binding.buttonBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_exoInfoFragment_to_exoListFragment)
        }

        binding.buttonDeleteTrash.setOnClickListener {
            binding.buttonDeleteTrash.setVisibility(View.GONE)
            binding.buttonDeleteSure.setVisibility(View.VISIBLE)
        }

        binding.buttonDeleteSure.setOnClickListener {

            if (currentExo != null) {
                exoViewModel.deleteExo(currentExo)
            }
            binding.buttonDeleteTrash.setVisibility(View.VISIBLE)
            binding.buttonDeleteSure.setVisibility(View.GONE)
            findNavController().navigate(R.id.action_exoInfoFragment_to_exoListFragment)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

}