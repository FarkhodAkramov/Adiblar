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
import com.example.adiblarfirebase.models.Writer
import com.google.firebase.firestore.FirebaseFirestore


class MumtozAdabiyotList : Fragment() {
    private val TAG = "MumtozAdabiyotList"
    lateinit var writersAdapter: WritersAdapter
    lateinit var writerList: ArrayList<Writer>
    lateinit var firestore: FirebaseFirestore
    lateinit var binding: FragmentMumtozAdabiyotListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMumtozAdabiyotListBinding.inflate(layoutInflater, container, false)
        val type = arguments?.getString("key")
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
                        if (type == writer.type) {
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

                                if (writer.saved == false) {
                                    writer.saved = true
                                    firestore.collection("writers").document(writer.id!!)
                                        .update("saved", true)
                                    
                                } else {
                                    writer.saved = false
                                    firestore.collection("writers").document(writer.id!!)
                                        .update("saved", false)
                                }

                            }
                        })
                    binding.rv.adapter = writersAdapter
                }
            }



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            MumtozAdabiyotList().apply {
                arguments = Bundle().apply {
                    putString("key", param1)
                }
            }
    }

}