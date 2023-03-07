package com.nuzhnov.knowledgemap.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.nuzhnov.graph.Roadmap
import com.nuzhnov.knowledgemap.R
import com.nuzhnov.knowledgemap.databinding.DetailsFragmentBinding
import com.nuzhnov.knowledgemap.presentation.adapters.ReferencesAdapter
import com.nuzhnov.knowledgemap.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.main_nav_graph)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(
            /* inflater = */ inflater,
            /* parent = */ container,
            /* attachToParent = */ false
        )

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val knowledgeName = args.knowledgeName
        val knowledge = viewModel.roadmap.getKnowledgeByName(knowledgeName)
        val referencesAdapter = ReferencesAdapter(knowledge.knowledgeSources)

        binding.apply {
            toolbar.title = knowledge.name
            title.text = knowledge.name
            description.text = knowledge.description
            referencesList.adapter = referencesAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun Roadmap.getKnowledgeByName(name: String) = knowledges.find { it.name == name }!!
}
