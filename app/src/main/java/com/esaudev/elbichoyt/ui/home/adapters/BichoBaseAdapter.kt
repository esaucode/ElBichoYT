package com.esaudev.elbichoyt.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.esaudev.elbichoyt.databinding.ItemBichoBinding
import com.esaudev.elbichoyt.domain.model.Bicho
import com.esaudev.elbichoyt.utils.load

abstract class BichoBaseAdapter (
    private val layoutId : Int
): RecyclerView.Adapter<BichoBaseAdapter.BichoViewHolder>() {

    class BichoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemBichoBinding.bind(itemView)

        fun render(bicho: Bicho) {

            binding.tvTile.text = bicho.title
            binding.tvDescription.text = bicho.description
            binding.ivImage.load(bicho.image)

        }
    }

    protected val diffCallback = object : DiffUtil.ItemCallback<Bicho>(){
        override fun areItemsTheSame(oldItem: Bicho, newItem: Bicho): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Bicho, newItem: Bicho): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    protected abstract val differ : AsyncListDiffer<Bicho>

    open var bichos : List<Bicho>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BichoViewHolder {
        return BichoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                layoutId,
                parent,
                false
            )
        )
    }

    protected var onItemClickListener : ((Bicho) -> Unit)? = null
    protected var onDeleteClickListener : ((Bicho) -> Unit)? = null

    fun setItemClickListener(listener: (Bicho) -> Unit){
        onItemClickListener = listener
    }

    fun setDeleteClickListener(listener: (Bicho) -> Unit){
        onDeleteClickListener = listener
    }

    override fun getItemCount(): Int {
        return bichos.size
    }

}