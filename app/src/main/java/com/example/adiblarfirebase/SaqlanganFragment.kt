package com.example.adiblarfirebase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.adiblarfirebase.adapters.WritersAdapter
import com.example.adiblarfirebase.databinding.FragmentMumtozAdabiyotListBinding
import com.example.adiblarfirebase.databinding.FragmentSaqlanganBinding
import com.example.adiblarfirebase.models.Writer
import com.google.firebase.firestore.FirebaseFirestore


class SaqlanganFragment : Fragment() {
    private val TAG = "SaqlanganFragment"
    lateinit var writersAdapter: WritersAdapter
    lateinit var writerList: ArrayList<Writer>
    lateinit var firestore: FirebaseFirestore
    lateinit var binding: FragmentSaqlanganBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSaqlanganBinding.inflate(layoutInflater, container, false)

        firestore = FirebaseFirestore.getInstance()
        writerList = ArrayList()
        firestore.collection("writers")
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    result?.forEach { queryDocumentSnapshot ->
                        Log.d(
                            TAG,
                            "onCreate: ${queryDocumentSnapshot.id}->${queryDocumentSnapshot.data}"
                        )

                        val writer = queryDocumentSnapshot.toObject(Writer::class.java)
                        if (writer.saved!!) {
                            writerList.add(writer)
                        }
                    }

                    writersAdapter =
                        WritersAdapter(writerList, object : WritersAdapter.OnItemClickListener {
                            override fun onItemClickListener(writer: Writer) {
                                val bundle = Bundle()
                                bundle.putSerializable("key", writer)
                                findNavController().navigate(R.id.infoFragment, bundle)
                            }

                            override fun onClickSave(writer: Writer) {

                            }

                        })
                    binding.rv.adapter = writersAdapter
                }
            }


        return binding.root
    }


}