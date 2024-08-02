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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.data.ExoInventoryViewModel
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.Food
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentExoHistoryBinding
import com.example.finalgymlog.databinding.FragmentExoInfoBinding
import java.io.File

class ExoHistoryFragment : Fragment() {

    private lateinit var mExoViewModel: ExoViewModel
    private val sessionViewModel: SessionViewModel by activityViewModels()
    private var _binding: FragmentExoHistoryBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExoHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mExoViewModel = ViewModelProvider(this).get(ExoViewModel::class.java)
        val allSessions = sessionViewModel.readAllSession.value


        val currentExo = sharedViewModel.getCurrentExo().value
        val exoName = currentExo?.name
        exoName?.let { mExoViewModel.getAllInstances(it) }?.observe(viewLifecycleOwner){
            refreshExoHistoryUI(it, allSessions)
        }

        return root
    }



    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }


    private fun refreshExoHistoryUI(exoList: List<Exo>, allSessions: List<Session>?) {
        if(exoList.size == 1){
            binding.emptyHistoryMessage.setVisibility(View.VISIBLE)
        }else{
            binding.emptyHistoryMessage.setVisibility(View.GONE)

            val thisExoHistoryFragment = this
            binding.recyclerViewHistory.apply {
                layoutManager = GridLayoutManager(activity?.applicationContext, 2)
                adapter =
                    allSessions?.let { ExoHistoryListAdapter(exoList, thisExoHistoryFragment, it) }
            }
        }
    }

}