package com.example.finalgymlog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.finalgymlog.databinding.FragmentGeneralInfoBinding

class GeneralInfoFragment : Fragment() {

    private var _binding: FragmentGeneralInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGeneralInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreferences = requireActivity().getSharedPreferences("MyPreferenceGender", Context.MODE_PRIVATE)
        val gender = sharedPreferences.getString("gender", "male")
        println("gender : $gender")

        if(gender == "male"){
            binding.infoContact.setText("Contact me at: joe.najm@hotmail.com. You will find all the instructions on the github page")
        }
        else if(gender == "female"){
            binding.infoContact.setText("Contact me on instagram @joe_najm. You will find all the instructions on the github page")
        }


        binding.buttonBackArrow.setOnClickListener{
            findNavController().navigate(R.id.action_generalInfoFragment_to_sessionListFragment)
        }


        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}