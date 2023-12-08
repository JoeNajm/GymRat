package com.example.finalgymlog

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.databinding.FragmentAddSessionBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

class AddSessionFragment : Fragment() {
    private lateinit var mSessionViewModel: SessionViewModel
    private var _binding: FragmentAddSessionBinding? = null
    private val binding get() = _binding!!


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddSessionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mSessionViewModel = ViewModelProvider(this).get(SessionViewModel::class.java)



        binding.addBtn.setOnClickListener {
            val formattedDate = getCurrentDateFormatted()
            insertDataToDatabase(formattedDate)
        }

        return root
    }

    private fun insertDataToDatabase(formattedDate: String) {

        val name = binding.addSessionName.text.toString()
        var weight = binding.weight.text.toString().toDouble()
        val comment = binding.addSessionComment.text.toString()


        if (name != "") {
            // Create User Object
            weight = (weight * 100.0).roundToInt() / 100.0
            val session = Session(0, name, formattedDate, comment, weight)
            // Add Data to Database
            mSessionViewModel.addSession(session)
            Toast.makeText(requireContext(), "No Pain No Gain!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigate(R.id.action_addSessionFragment_to_sessionListFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out the name", Toast.LENGTH_LONG)
                .show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateFormatted(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE dd/MM", Locale.ENGLISH)
        return currentDate.format(formatter)
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}