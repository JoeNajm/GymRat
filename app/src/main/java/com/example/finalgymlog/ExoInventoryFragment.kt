package com.example.finalgymlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.data.ExoInventoryViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentExoInventoryBinding

class ExoInventoryFragment : Fragment() {

    private var _binding: FragmentExoInventoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ExoInventoryViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExoInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this).get(ExoInventoryViewModel::class.java)

        viewModel.readAllExoInventory.observe(viewLifecycleOwner, {
            refreshExosUI(it)
        })

        binding.buttonChest.setOnClickListener {
            setAllButtonsRed()
            binding.buttonChest.setBackgroundColor(resources.getColor(R.color.green))

            viewModel.readExoByType("Chest").observe(viewLifecycleOwner) {
                refreshExosUI(it)
                if(it.isEmpty()){
                    binding.textNoExos.visibility = View.VISIBLE
                } else{
                    binding.textNoExos.visibility = View.GONE
                }
            }
        }

        binding.buttonBack.setOnClickListener {
            setAllButtonsRed()
            binding.buttonBack.setBackgroundColor(resources.getColor(R.color.green))

            viewModel.readExoByType("Back").observe(viewLifecycleOwner) {
                refreshExosUI(it)
                if(it.isEmpty()){
                    binding.textNoExos.visibility = View.VISIBLE
                } else{
                    binding.textNoExos.visibility = View.GONE
                }
            }
        }

        binding.buttonShoulders.setOnClickListener {
            setAllButtonsRed()
            binding.buttonShoulders.setBackgroundColor(resources.getColor(R.color.green))

            viewModel.readExoByType("Shoulders").observe(viewLifecycleOwner) {
                refreshExosUI(it)
                if(it.isEmpty()){
                    binding.textNoExos.visibility = View.VISIBLE
                } else{
                    binding.textNoExos.visibility = View.GONE
                }
            }
        }

        binding.buttonCardio.setOnClickListener {
            setAllButtonsRed()
            binding.buttonCardio.setBackgroundColor(resources.getColor(R.color.green))

            viewModel.readExoByType("Cardio").observe(viewLifecycleOwner) {
                refreshExosUI(it)
                if(it.isEmpty()){
                    binding.textNoExos.visibility = View.VISIBLE
                } else{
                    binding.textNoExos.visibility = View.GONE
                }
            }
        }

        binding.buttonArms.setOnClickListener {
            setAllButtonsRed()
            binding.buttonArms.setBackgroundColor(resources.getColor(R.color.green))

            viewModel.readExoByType("Arms").observe(viewLifecycleOwner) {
                refreshExosUI(it)
                if(it.isEmpty()){
                    binding.textNoExos.visibility = View.VISIBLE
                } else{
                    binding.textNoExos.visibility = View.GONE
                }
            }
        }

        binding.buttonCore.setOnClickListener {
            setAllButtonsRed()
            binding.buttonCore.setBackgroundColor(resources.getColor(R.color.green))

            viewModel.readExoByType("Core").observe(viewLifecycleOwner) {
                refreshExosUI(it)
                if(it.isEmpty()){
                    binding.textNoExos.visibility = View.VISIBLE
                } else{
                    binding.textNoExos.visibility = View.GONE
                }
            }
        }

        binding.buttonLower.setOnClickListener {
            setAllButtonsRed()
            binding.buttonLower.setBackgroundColor(resources.getColor(R.color.green))

            viewModel.readExoByType("Legs").observe(viewLifecycleOwner) {
                refreshExosUI(it)
                if(it.isEmpty()){
                    binding.textNoExos.visibility = View.VISIBLE
                } else{
                    binding.textNoExos.visibility = View.GONE
                }
            }
        }

        binding.buttonOther.setOnClickListener {
            setAllButtonsRed()
            binding.buttonOther.setBackgroundColor(resources.getColor(R.color.green))

            viewModel.readExoByType("Other").observe(viewLifecycleOwner) {
                refreshExosUI(it)
                if(it.isEmpty()){
                    binding.textNoExos.visibility = View.VISIBLE
                } else{
                    binding.textNoExos.visibility = View.GONE
                }
            }
        }

        binding.buttonAll.setOnClickListener {
            setAllButtonsRed()
            binding.buttonAll.setBackgroundColor(resources.getColor(R.color.green))

            viewModel.readAllExoInventory.observe(viewLifecycleOwner) {
                refreshExosUI(it)
                if(it.isEmpty()){
                    binding.textNoExos.visibility = View.VISIBLE
                } else{
                    binding.textNoExos.visibility = View.GONE
                }
            }
        }

        binding.buttonBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_exoInventoryFragment_to_sessionListFragment)
        }

        binding.floatingActionButtonExo.setOnClickListener {
            findNavController().navigate(R.id.action_exoInventoryFragment_to_addExoInventoryFragment)
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    fun onClick(exoinventory: ExoInventory){
        sharedViewModel.setCurrentExoInventory(exoinventory)
        findNavController().navigate(R.id.action_exoInventoryFragment_to_exoInventoryDetailsFragment)
    }

    private fun refreshExosUI(exoList: List<ExoInventory>) {
//        if(exoList.isEmpty()){
//            // TODO: If user deletes all data, then not initialize again ...
//            initializeExoInventory()
//        }
        // Passing the LayoutManager and Adapter to the RecyclerView of the Store
        val thisExoFragment = this
        binding.recyclerViewExos.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext, 2)
            adapter = ExoInventoryListAdapter(exoList, thisExoFragment, null)
        }
    }

    private fun setAllButtonsRed(){
        binding.buttonChest.setBackgroundColor(resources.getColor(R.color.red))
        binding.buttonBack.setBackgroundColor(resources.getColor(R.color.red))
        binding.buttonShoulders.setBackgroundColor(resources.getColor(R.color.red))
        binding.buttonCardio.setBackgroundColor(resources.getColor(R.color.red))
        binding.buttonArms.setBackgroundColor(resources.getColor(R.color.red))
        binding.buttonCore.setBackgroundColor(resources.getColor(R.color.red))
        binding.buttonLower.setBackgroundColor(resources.getColor(R.color.red))
        binding.buttonOther.setBackgroundColor(resources.getColor(R.color.red))
        binding.buttonAll.setBackgroundColor(resources.getColor(R.color.red))
    }
}