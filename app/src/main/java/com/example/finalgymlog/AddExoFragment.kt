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
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentAddExoBinding
import com.example.finalgymlog.databinding.FragmentAddSessionBinding


class AddExoFragment : Fragment() {
    private lateinit var mExoViewModel: ExoViewModel
    private var _binding: FragmentAddExoBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddExoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mExoViewModel = ViewModelProvider(this).get(ExoViewModel::class.java)

        binding.addBtnExo.setOnClickListener {
            insertDataToDatabase()
        }

        return root
    }

    private fun insertDataToDatabase() {

        val name = binding.addExoName.text.toString()
        val reps = binding.addExoReps.text.toString()
        val comment = binding.addExoComments.text.toString()
        val weights = binding.addExoWeights.text.toString()

        val session = sharedViewModel.getCurrentSession().value



        if (inputCheck(name, reps, weights)) {
            val exo = session?.id?.let { Exo(0, name, reps, weights, comment, it) }
            // Add Data to Database
            exo?.let { mExoViewModel.addExo(it) }
            Toast.makeText(requireContext(), "successfully added!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigate(R.id.action_addExoFragment_to_exoListFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(name: String, rep: String, weights: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(rep) && TextUtils.isEmpty(weights))
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}