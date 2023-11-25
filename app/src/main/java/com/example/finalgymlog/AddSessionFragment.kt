package com.example.finalgymlog

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.databinding.FragmentAddSessionBinding

class AddSessionFragment : Fragment() {
    private lateinit var mSessionViewModel: SessionViewModel
    private var _binding: FragmentAddSessionBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddSessionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mSessionViewModel = ViewModelProvider(this).get(SessionViewModel::class.java)

        binding.addBtn.setOnClickListener {
            insertDataToDatabase()
        }

        return root
    }

    private fun insertDataToDatabase() {

        val name = binding.addSessionName.text.toString()
        val date = binding.addSessionDate.text.toString()
        val comment = binding.addSessionComment.text.toString()


        if (inputCheck(name, date)) {
            // Create User Object
            val session = Session(0, name, date, comment)
            // Add Data to Database
            mSessionViewModel.addSession(session)
            Toast.makeText(requireContext(), "No Pain No Gain!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigate(R.id.action_addSessionFragment_to_sessionListFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(name: String, date: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(date))
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}