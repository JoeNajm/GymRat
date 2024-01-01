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
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.data.ExoInventoryViewModel
import com.example.finalgymlog.data.FridgeFood
import com.example.finalgymlog.data.FridgeFoodViewModel
import com.example.finalgymlog.databinding.FragmentAddExoInventoryBinding
import com.example.finalgymlog.databinding.FragmentAddFoodFridgeBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import kotlin.math.roundToInt

class AddExoInventoryFragment : Fragment() {

    private var _binding: FragmentAddExoInventoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var uriItemPicture: Uri
    private var uriCompressedPicture: Uri? = null
    private lateinit var uriItemResizedPicture: Uri
    private lateinit var viewmodel: ExoInventoryViewModel


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddExoInventoryBinding.inflate(inflater, container, false)
        viewmodel = ViewModelProvider(this).get(ExoInventoryViewModel::class.java)
        val root: View = binding.root

        var state = "Upper Body"
        binding.buttonUpper.setOnClickListener {
            state = "Upper Body"
            binding.textAnswerExo.text = state
            binding.buttonUpper.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonLower.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonOther.setBackgroundColor(resources.getColor(R.color.red))
        }
        binding.buttonLower.setOnClickListener {
            state = "Legs"
            binding.textAnswerExo.text = state
            binding.buttonLower.setBackgroundColor(resources.getColor(R.color.green))
            binding.buttonUpper.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonOther.setBackgroundColor(resources.getColor(R.color.red))
        }

        binding.buttonOther.setOnClickListener {
            state = "Other"
            binding.textAnswerExo.text = state
            binding.buttonLower.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonUpper.setBackgroundColor(resources.getColor(R.color.red))
            binding.buttonOther.setBackgroundColor(resources.getColor(R.color.green))
        }

        binding.addImageButton.setOnClickListener {
            val imgIntent = Intent(Intent.ACTION_GET_CONTENT)
            imgIntent.type = "image/*"
            resultLauncher.launch(imgIntent)
        }

        binding.addBtnExo.setOnClickListener {
            val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            val randomString: String = List(10) { alphabet.random() }.joinToString("")
            var pathOfImage = "drawable://" + R.drawable.rat

            if(uriCompressedPicture != null){
                pathOfImage = randomString + ".jpg"

                val contentResolver = requireContext().contentResolver
                val inputStream = uriCompressedPicture?.let { it1 -> contentResolver.openInputStream(it1) }
                val file = File(requireContext().filesDir, pathOfImage)

                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
            }

            val name = binding.addExoName.text.toString()
            if (name != "") {
                val exo = ExoInventory(0, name, pathOfImage, state)
                exo?.let { viewmodel.addExoInventory(it) }

                findNavController().navigate(R.id.action_addExoInventoryFragment_to_exoInventoryFragment)
            } else{
                Toast.makeText(requireContext(), "Please fill name", Toast.LENGTH_LONG)
                    .show()
            }
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
                800, 800, false
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