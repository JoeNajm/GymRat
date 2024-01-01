package com.example.finalgymlog

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.data.ExoInventoryViewModel
import com.example.finalgymlog.data.FridgeFood
import com.example.finalgymlog.data.FridgeFoodViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.FragmentExoInventoryDetailsBinding
import com.example.finalgymlog.databinding.FragmentFridgeFoodInfoBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class ExoInventoryDetailsFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentExoInventoryDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExoInventoryViewModel by activityViewModels()
    private lateinit var uriItemPicture: Uri
    private var uriCompressedPicture: Uri? = null
    private lateinit var uriItemResizedPicture: Uri

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExoInventoryDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val currentExo = sharedViewModel.getCurrentExoInventory().value
        binding.exoName.setText(currentExo?.name)
        val PathOfImage = currentExo?.imagepath

        if(currentExo!!.type == "Upper Body"){
            binding.buttonUpper.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonLower.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonOther.setBackgroundColor(resources.getColor(R.color.red))
        } else if(currentExo.type == "Legs"){
            binding.buttonUpper.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonLower.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonOther.setBackgroundColor(resources.getColor(R.color.red))
        } else {
            binding.buttonUpper.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonLower.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonOther.setBackgroundColor(resources.getColor(R.color.green))
        }

        var state = currentExo.type
        binding.buttonUpper.setOnClickListener {
            state = "Upper Body"
            binding.buttonUpper.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonLower.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonOther.setBackgroundColor(resources.getColor(R.color.red))
            binding.textAnswerExo.text = state
        }
        binding.buttonLower.setOnClickListener {
            state = "Legs"
            binding.buttonUpper.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonLower.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonOther.setBackgroundColor(resources.getColor(R.color.red))
            binding.textAnswerExo.text = state
        }

        binding.buttonOther.setOnClickListener {
            state = "Other"
            binding.buttonUpper.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonLower.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonOther.setBackgroundColor(resources.getColor(R.color.green))
            binding.textAnswerExo.text = state
        }
        binding.textAnswerExo.text = state

        if(PathOfImage!!.contains("drawable", ignoreCase = true)){
            val intId = PathOfImage.substring(11, PathOfImage.length).toInt()
            binding.addImageButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), intId))
        } else{
            val file = File(requireContext().filesDir, PathOfImage)
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            binding.addImageButton.setImageBitmap(bitmap)
        }

        binding.buttonBackArrow.setOnClickListener{
            findNavController().navigate(R.id.action_exoInventoryDetailsFragment_to_exoInventoryFragment)
        }

        binding.addImageButton.setOnClickListener {
            val imgIntent = Intent(Intent.ACTION_GET_CONTENT)
            imgIntent.type = "image/*"
            resultLauncher.launch(imgIntent)

        }

        binding.buttonBackExo.setOnClickListener {
            var pathOfImage = currentExo.imagepath
            // TODO: CHANGE PATH IF WE GO FROM NONE TO IMAGE
            if(uriCompressedPicture != null){

                if(pathOfImage.contains("drawable", ignoreCase = true)){
                    val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
                    val randomString: String = List(10) { alphabet.random() }.joinToString("")
                    pathOfImage = randomString + ".jpg"
                }

                val contentResolver = requireContext().contentResolver
                val inputStream = uriCompressedPicture?.let { it1 -> contentResolver.openInputStream(it1) }
                val file = File(requireContext().filesDir, pathOfImage)

                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
            }

            val name = binding.exoName.text.toString()

            val updatedExoInventory = ExoInventory(
                currentExo!!.id,
                name,
                pathOfImage,
                state.toString()
            )
            viewModel.updateExoInventory(updatedExoInventory)
            sharedViewModel.setCurrentExoInventory(updatedExoInventory)

            findNavController().navigate(R.id.action_exoInventoryDetailsFragment_to_exoInventoryFragment)
        }

        binding.buttonDeleteTrash.setOnClickListener {
            binding.buttonDeleteTrash.setVisibility(View.GONE)
            binding.buttonDeleteSure.setVisibility(View.VISIBLE)
        }

        binding.buttonDeleteSure.setOnClickListener {

            if (currentExo != null) {
                viewModel.deleteExoInventory(currentExo)
            }

            binding.buttonDeleteTrash.setVisibility(View.VISIBLE)
            binding.buttonDeleteSure.setVisibility(View.GONE)
            findNavController().navigate(R.id.action_exoInventoryDetailsFragment_to_exoInventoryFragment)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }


    @RequiresApi(Build.VERSION_CODES.P)
    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                uriItemPicture = result.data?.data!!
                resizePhoto()
                binding.addImageButton.setImageURI(uriCompressedPicture)
            }
        }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun resizePhoto() {
        val bitmapPicture: Bitmap

        val source = ImageDecoder.createSource(activity?.contentResolver!!,
            uriItemPicture
        )
        bitmapPicture = ImageDecoder.decodeBitmap(source)

        val drawableExoImage = BitmapDrawable(
            resources,
            Bitmap.createScaledBitmap(
                bitmapPicture,
                500, 500, false
            )
        )

//        Updates the liveData
        uriItemResizedPicture = getImageUri(context, drawableExoImage.getBitmap())
        uriCompressedPicture = compressImage(uriItemResizedPicture, requireActivity())!!
    }

    private fun getImageUri(inContext: Context?, inImage: Bitmap): Uri {

        val tempFile = File.createTempFile("temprentpk", ".png")
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val bitmapData = bytes.toByteArray()

        val fileOutPut = FileOutputStream(tempFile)
        fileOutPut.write(bitmapData)
        fileOutPut.flush()
        fileOutPut.close()
        return Uri.fromFile(tempFile)
    }

    fun compressImage(imageUri: Uri, context: Context?): Uri? {
        if (context == null) {
            return null
        }
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val compressedImage = byteArrayOutputStream.toByteArray()
        val cacheDir = context.cacheDir
        val file = File.createTempFile("compressed_image_profile", ".jpeg", cacheDir)
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(compressedImage)
        fileOutputStream.flush()
        fileOutputStream.close()
        return Uri.fromFile(file)
    }
}