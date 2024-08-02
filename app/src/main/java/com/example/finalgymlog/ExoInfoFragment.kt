package com.example.finalgymlog

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.data.ExoInventoryViewModel
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentExoInfoBinding
import com.example.finalgymlog.databinding.FragmentExoListBinding
import com.example.finalgymlog.databinding.FragmentSessionListBinding
import java.io.File


class ExoInfoFragment : Fragment() {

    private val exoViewModel: ExoViewModel by activityViewModels()
    private var _binding: FragmentExoInfoBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val exoInventoryViewModel: ExoInventoryViewModel by activityViewModels()

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

        //TODO: INSTEAD OF OBSERVER, GIVE PATH IN SHAREDVIEWMODEL... OR JUST REMOVE IMAGE ;)

        val savedExo = sharedViewModel.getCurrentExoInventoryList()

        println("savedExoList: $savedExo")
        val name = currentExo!!.name.lowercase()
        var found = false
        for(e in savedExo.value!!)
            if(name in e.name.lowercase()){
            found = true
            if(e.imagepath.contains("drawable", ignoreCase = true)){
                val PathOfImage = e.imagepath
                val intId = PathOfImage.substring(11, PathOfImage.length).toInt()
                binding.exoImage.setImageDrawable(context?.let {
                    ContextCompat.getDrawable(
                        it, intId)
                })
            } else{
                val PathOfImage = e.imagepath
                val file = File(requireContext().filesDir, PathOfImage)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                binding.exoImage.setImageBitmap(bitmap)
            }
        }
        if(found == false){
            binding.exoImage.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.rat) })
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

        binding.buttonExoHistory.setOnClickListener {
            findNavController().navigate(R.id.action_exoInfoFragment_to_exoHistoryFragment)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

}