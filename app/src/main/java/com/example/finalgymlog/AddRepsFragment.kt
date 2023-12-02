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
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentAddRepsBinding
import kotlin.math.roundToInt


class AddRepsFragment : Fragment() {
    private lateinit var mExoViewModel: ExoViewModel
    private var _binding: FragmentAddRepsBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddRepsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mExoViewModel = ViewModelProvider(this).get(ExoViewModel::class.java)

        val currentExo = sharedViewModel.getCurrentExo().value

        if (currentExo != null) {
            binding.textIntroAddReps.setText(currentExo.name)
        }

        binding.addBtnReps.setOnClickListener {
            if ((binding.addExoWeights.text.toString() != "") && (binding.addExoReps.text.toString() != "")){
                if (currentExo != null) {
                    insertDataToDatabase(currentExo)
                    findNavController().navigate(R.id.action_addRepsFragment_to_exoListFragment)
                }
            }
            else{
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                    .show()
            }
        }

        return root
    }

    private fun insertDataToDatabase(currentExo: Exo) {

        var reps = 0.0
        var weights = 0.0

        var repsStr = ""
        var weightsStr = ""

        if (!binding.addExoReps.text.toString().isEmpty()){
            reps = (binding.addExoReps.text.toString().toDouble() * 100.0).roundToInt() / 100.0
            if(reps.rem(1).equals(0.0)){
                repsStr = reps.toInt().toString()
            }else{
                repsStr = reps.toString()
            }

        }
        if (!binding.addExoWeights.text.toString().isEmpty()){
            weights = binding.addExoWeights.text.toString().toDouble()
            if(weights.rem(1).equals(0.0)){
                weightsStr = weights.toInt().toString()
            }else{
                weightsStr = weights.toString()
            }
        }

        val newReps: String
        if(currentExo.reps == ""){
            newReps = repsStr
        }else{
            newReps = currentExo!!.reps + " - " + repsStr
        }

        val newWeights: String
        if(currentExo.weights == ""){
            newWeights = weightsStr
        }else{
            newWeights = currentExo!!.weights + " - " + weightsStr
        }

        val updatedExo = Exo(
            currentExo.id,
            currentExo.name,
            newReps,
            newWeights,
            currentExo.comment,
            currentExo.parentId
        )
        mExoViewModel.updateExo(updatedExo)

    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}