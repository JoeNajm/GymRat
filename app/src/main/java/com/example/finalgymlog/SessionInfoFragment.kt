package com.example.finalgymlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentSessionInfoBinding

class SessionInfoFragment : Fragment() {

    private val sessionViewModel: SessionViewModel by activityViewModels()
    private val exoViewModel: ExoViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentSessionInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSessionInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val currentSession = sharedViewModel.getCurrentSession().value
        binding.sessionName.setText(currentSession?.name)
        binding.sessionComments.setText(currentSession?.comment)
        binding.sessionDate.setText(currentSession?.date)

        binding.weight.setText(currentSession?.body_weight.toString())

        if("leg" in currentSession?.name!!.lowercase()){
            binding.sessionImage.setImageResource(R.drawable.legs)
        }
        else if("upper" in currentSession.name.lowercase()){
            binding.sessionImage.setImageResource(R.drawable.upper)
        }
        else {
            binding.sessionImage.setImageResource(R.drawable.rat)
        }

        binding.buttonBackArrow.setOnClickListener{
            findNavController().navigate(R.id.action_sessionInfoFragment_to_exoListFragment)
        }

        binding.buttonBackSession.setOnClickListener {

            val weight = binding.weight.text.toString().toDouble()
            val updatedSession = Session(
                currentSession.id,
                binding.sessionName.text.toString(),
                binding.sessionDate.text.toString(),
                binding.sessionComments.text.toString(),
                weight
            )
            sessionViewModel.updateSession(updatedSession)
            sharedViewModel.setCurrentSession(updatedSession)

            findNavController().navigate(R.id.action_sessionInfoFragment_to_exoListFragment)
        }

        binding.buttonDeleteTrash.setOnClickListener {
            binding.buttonDeleteTrash.setVisibility(View.GONE)
            binding.buttonDeleteSure.setVisibility(View.VISIBLE)
        }

        binding.buttonDeleteSure.setOnClickListener {

//            exoViewModel.deleteExoByParentId(currentSession.id)
            sessionViewModel.deleteSession(currentSession)

            binding.buttonDeleteTrash.setVisibility(View.VISIBLE)
            binding.buttonDeleteSure.setVisibility(View.GONE)
            findNavController().navigate(R.id.action_sessionInfoFragment_to_sessionListFragment)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}