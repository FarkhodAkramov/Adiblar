package com.example.adiblarfirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

import androidx.navigation.fragment.findNavController
import com.example.adiblarfirebase.databinding.FragmentInfoBinding
import com.example.adiblarfirebase.models.Writer
import com.squareup.picasso.Picasso


@Suppress("UNREACHABLE_CODE")
class InfoFragment : Fragment() {
    lateinit var binding: FragmentInfoBinding
    lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).hide()
        binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        var writer = arguments?.getSerializable("key") as Writer
        binding.textView.text = writer.name
        binding.textView2.text = writer.period
        Picasso.get().load(writer.photo).into(binding.image)
        binding.information.text = writer.information
        if (writer.saved!!) {
            binding.notsave.visibility = View.INVISIBLE
        } else {

            binding.saved.visibility = View.INVISIBLE

        }

        binding.cardView2.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.saveCard.setOnClickListener {
            if (writer.saved == false) {
                writer.saved = true
                firestore.collection("writers").document(writer.id!!)
                    .update("saved", true)
                binding.notsave.visibility = View.INVISIBLE
                binding.saved.visibility = View.VISIBLE

            } else {
                writer.saved = false
                firestore.collection("writers").document(writer.id!!)
                    .update("saved", false)
                binding.notsave.visibility = View.VISIBLE
                binding.saved.visibility = View.INVISIBLE

            }
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).show()
    }


}