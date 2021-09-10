package com.example.adiblarfirebase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.adiblarfirebase.databinding.ItemRvBinding
import com.example.adiblarfirebase.models.Writer
import com.squareup.picasso.Picasso
import java.util.ArrayList
    class WritersAdapter(var list: ArrayList<Writer>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<WritersAdapter.Vh>() {
    inner class Vh(var itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(writer: Writer) {
            itemRvBinding.name.text = writer.name

            itemRvBinding.periodTv.text = writer.period
            if (writer.photo != null) {
                Picasso.get().load(writer.photo).into(itemRvBinding.image)
            }
            if (writer.saved!!) {
                itemRvBinding.saveIcon.visibility= View.INVISIBLE
            } else {

                itemRvBinding.saved.visibility= View.INVISIBLE

            }
            itemRvBinding.savePlace.setOnClickListener {
                writer.saved = true
            }

            itemRvBinding.root.setOnClickListener {
                onItemClickListener.onItemClickListener(writer)
            }
            itemRvBinding.saveIcon.setOnClickListener {
               onItemClickListener.onClickSave(writer)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClickListener(writer: Writer)
        fun onClickSave(writer: Writer)
    }
}