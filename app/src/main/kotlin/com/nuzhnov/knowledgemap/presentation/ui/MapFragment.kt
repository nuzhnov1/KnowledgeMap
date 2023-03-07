package com.nuzhnov.knowledgemap.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import com.nuzhnov.graph.Roadmap
import com.nuzhnov.knowledgemap.R
import com.nuzhnov.knowledgemap.databinding.MapFragmentBinding
import com.nuzhnov.knowledgemap.presentation.adapters.NodeAdapter
import com.nuzhnov.knowledgemap.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.bandb.graphview.graph.Graph
import dev.bandb.graphview.graph.Node
import dev.bandb.graphview.layouts.layered.SugiyamaArrowEdgeDecoration
import dev.bandb.graphview.layouts.layered.SugiyamaConfiguration
import dev.bandb.graphview.layouts.layered.SugiyamaLayoutManager

@AndroidEntryPoint
class MapFragment : Fragment() {

    private var _binding: MapFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.main_nav_graph)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MapFragmentBinding.inflate(
            /* inflater = */ inflater,
            /* parent = */ container,
            /* attachToParent = */ false
        )

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupGraph()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupGraph() {
        val recycler = binding.recycler
        val configuration = SugiyamaConfiguration.Builder()
            .setLevelSeparation(100)
            .setNodeSeparation(50)
            .build()

        recycler.apply {
            val graph = viewModel.roadmap.toGraph()

            layoutManager = SugiyamaLayoutManager(requireContext(), configuration)
            addItemDecoration(SugiyamaArrowEdgeDecoration())
            adapter = NodeAdapter(::onNodeClick).apply { submitGraph(graph) }
        }
    }

    private fun Roadmap.toGraph() = Graph().apply {
        knowledges.forEach { addNode(Node(it)) }
        arcs.forEach { arc ->
            val startingKnowledge = arc.startingKnowledge
            val nextKnowledge = arc.nextKnowledge

            val sourceNode = getNodeAtPosition(startingKnowledge)!!
            val destinationNode = getNodeAtPosition(nextKnowledge)!!

            addEdge(sourceNode, destinationNode)
        }
    }

    private fun onNodeClick(view: View) {
        val textView = view as TextView
        val text = textView.text.toString()
        val navigateAction = MapFragmentDirections.navigateToDetails(text)

        textView.findNavController().navigate(navigateAction)
    }
}
