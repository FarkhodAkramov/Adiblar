package com.example.adiblarfirebase.adminFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebStorage
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.adiblarfirebase.MainActivity
import com.example.adiblarfirebase.R
import com.example.adiblarfirebase.databinding.FragmentAdminBinding
import com.example.adiblarfirebase.models.Writer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class Admin : Fragment() {
    private val TAG = "Admin"

    lateinit var binding: FragmentAdminBinding
    lateinit var firebasrStorage: FirebaseStorage
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var reference: StorageReference
    var imgUrl: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (requireActivity() as MainActivity).hide()
        binding = FragmentAdminBinding.inflate(layoutInflater, container, false)
        var typeList = arrayOf("Mumtoz adabiyoti", "O’zbek adabiyoti", "Jahon adabiyoti")
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebasrStorage = FirebaseStorage.getInstance()
        reference = firebasrStorage.getReference("images")
        binding.typeSpiner.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            typeList
        )
        binding.typeSpiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val choose = typeList[position]
                binding.tv.text = choose
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        binding.image.setOnClickListener {
            getImageFromCamera()
        }

        binding.saveBtn.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val period = binding.period.text.toString()
            val saved = false
            val information = binding.information.text.toString()
            val type = binding.tv.text.toString()
            val writer =
               Writer(
                    id = "",
                    name = name,
                    period = period,
                    type = type,
                    information = information,
                    photo = imgUrl,
                    saved = saved
                )
            firebaseFirestore.collection("writers").add(writer)
                .addOnSuccessListener { documnetReference ->
                    Log.d(TAG, "onCreate: ${documnetReference.id}")
                    writer.id = documnetReference.id
                        firebaseFirestore.collection("writers").document(documnetReference.id).update("id",documnetReference.id)
                }.addOnFailureListener { e ->
                    Log.d(TAG, "onCreate: ${e.message}")

                }
            findNavController().navigate(R.id.mainFragment)

        }

        return binding.root
    }


    private fun getImageFromCamera() {
        getImageContent.launch("image/*")
    }

    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding.image.setImageURI(uri)
            val m = System.currentTimeMillis()
            val uploadTask = reference.child(m.toString()).putFile(uri)

            uploadTask.addOnSuccessListener {
                if (it.task.isSuccessful) {
                    val downloadUrl = it.metadata?.reference?.downloadUrl
                    downloadUrl?.addOnSuccessListener { imgUri ->
                        imgUrl = imgUri.toString()

                    }
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).show()
    }
}