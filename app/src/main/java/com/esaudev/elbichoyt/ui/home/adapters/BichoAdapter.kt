package com.esaudev.elbichoyt.ui.home.adapters

import android.content.Context
import androidx.recyclerview.widget.AsyncListDiffer
import com.esaudev.elbichoyt.R
import com.esaudev.elbichoyt.domain.model.Bicho
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.android.synthetic.main.item_bicho.view.*
import javax.inject.Inject

class BichoAdapter @Inject constructor(

) : BichoBaseAdapter(R.layout.item_bicho) {

    override val differ = AsyncListDiffer(this, diffCallback)
    fun submitList(list : List<Bicho>) = differ.submitList(list)


    override fun onBindViewHolder(holder: BichoViewHolder, position: Int) {
        val bicho = bichos[position]

        holder.render(bicho)

        holder.itemView.btnDelete.setOnClickListener {
            onDeleteClickListener?.let { click ->
                click(bicho)
            }
        }

        holder.itemView.apply {

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(bicho)
                }
            }
        }
    }
}