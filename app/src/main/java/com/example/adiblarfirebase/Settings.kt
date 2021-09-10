package com.example.adiblarfirebase

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.adiblarfirebase.databinding.FragmentSettingsBinding


class Settings : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        binding.adding.setOnClickListener {
            findNavController().navigate(R.id.admin)
        }
        binding.share.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/pain"
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Ulashish")
            startActivity(Intent.createChooser(shareIntent,"Share via"))
        }
        return binding.root
    }


}