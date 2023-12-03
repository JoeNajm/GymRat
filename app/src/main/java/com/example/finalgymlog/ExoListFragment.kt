package com.example.finalgymlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoViewModel
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentExoListBinding
import com.example.finalgymlog.databinding.FragmentSessionListBinding


class ExoListFragment : Fragment() {

    private val viewModel: ExoViewModel by activityViewModels()
    private val sessionViewModel: SessionViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentExoListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExoListBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val args: ExoListFragmentArgs by navArgs()
        val sessionID = sharedViewModel.getCurrentSession().value?.id
        val session = sharedViewModel.getCurrentSession().value

        if (session != null) {
            binding.textNameSession.setText(session.name)
            binding.textDateSession.setText(session.date)
        }
        else{
            binding.textNameSession.setText("Go back")
            binding.textDateSession.setText("Go back")
        }

        if(sessionID == null){
            binding.buttonSessionInfo.setVisibility(View.GONE)
            binding.floatingActionButtonExo.setVisibility(View.GONE)
        }
        else{
            binding.buttonSessionInfo.setVisibility(View.VISIBLE)
            binding.floatingActionButtonExo.setVisibility(View.VISIBLE)
        }

        sessionID?.let {
            viewModel.readExoByParentId(it).observe(viewLifecycleOwner, {
                refreshExoUI(it)
            })
        }

        binding.floatingActionButtonExo.setOnClickListener {
            findNavController().navigate(R.id.action_exoListFragment_to_addExoFragment)
        }

        binding.buttonBackExo.setOnClickListener {
            findNavController().navigate(R.id.action_exoListFragment_to_sessionListFragment)
        }

        binding.buttonSessionInfo.setOnClickListener {
            findNavController().navigate(R.id.action_exoListFragment_to_sessionInfoFragment)
        }

        binding.buttonFood.setOnClickListener {
            findNavController().navigate(R.id.action_exoListFragment_to_foodFragment)
        }

        return root
    }

    fun onClick(exo: Exo) {
        sharedViewModel.setCurrentExo(exo)
        findNavController().navigate(R.id.action_exoListFragment_to_exoInfoFragment)
    }

    fun onClickRep(exo: Exo) {
        sharedViewModel.setCurrentExo(exo)
        findNavController().navigate(R.id.action_exoListFragment_to_addRepsFragment)
    }

    private fun refreshExoUI(exoList: List<Exo>) {
        if(exoList.size == 0){
            binding.textAddExo.setVisibility(View.VISIBLE)
        }else{
            binding.textAddExo.setVisibility(View.GONE)
        }
        // Passing the LayoutManager and Adapter to the RecyclerView of the Store
        val thisExoFragment = this
        binding.recyclerViewExo.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext, 2)
            adapter = ExoListAdapter(exoList, thisExoFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}