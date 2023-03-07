package com.nuzhnov.knowledgemap.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nuzhnov.graph.Knowledge
import com.nuzhnov.knowledgemap.databinding.NodeBinding
import com.nuzhnov.knowledgemap.presentation.util.OnClickListener
import dev.bandb.graphview.AbstractGraphAdapter

internal class NodeAdapter(
    private val onClickListener: OnClickListener
) : AbstractGraphAdapter<NodeAdapter.NodeHolder>() {

    override fun onBindViewHolder(holder: NodeHolder, position: Int) {
        val knowledge = getNodeData(position) as Knowledge
        holder.bind(knowledge, onClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeHolder {
        val binding = NodeBinding.inflate(
            /* inflater = */ LayoutInflater.from(parent.context),
            /* parent = */ parent,
            /* attachToParent = */ false
        )

        return NodeHolder(binding)
    }


    class NodeHolder(private val binding: NodeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(knowledge: Knowledge, onClickListener: OnClickListener) {
            binding.nodeButton.text = knowledge.name
            binding.nodeButton.setOnClickListener(onClickListener)
        }
    }
}
