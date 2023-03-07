package com.nuzhnov.knowledgemap.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nuzhnov.knowledgemap.databinding.ReferenceItemBinding

class ReferencesAdapter(
    private val referencesList: List<String>
) : RecyclerView.Adapter<ReferencesAdapter.ReferenceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferenceHolder {
        val binding = ReferenceItemBinding.inflate(
            /* inflater = */ LayoutInflater.from(parent.context),
            /* parent = */ parent,
            /* attachToParent = */ false
        )

        return ReferenceHolder(binding)
    }

    override fun getItemCount(): Int = referencesList.size

    override fun onBindViewHolder(holder: ReferenceHolder, position: Int) {
        holder.bind(referencesList[position])
    }


    class ReferenceHolder(
        private val binding: ReferenceItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reference: String) {
            binding.reference.text = reference
        }
    }
}
