package com.nuzhnov.knowledgemap.data.local

import com.nuzhnov.graph.Arc
import com.nuzhnov.graph.Roadmap
import javax.inject.Inject

internal class AndroidRoadmapSource @Inject constructor() {
    val roadmap = Roadmap(
        Arc(rootNode, chooseLanguageNode),
        Arc(chooseLanguageNode, kotlinNode),
        Arc(chooseLanguageNode, javaNode),
        Arc(chooseLanguageNode, fundamentalsNode),
        Arc(fundamentalsNode, vcsNode),
        Arc(vcsNode, applicationNode),
        Arc(applicationNode, architectureNode),
        Arc(applicationNode, coreComponentsNode),
        Arc(applicationNode, navigationNode),
        Arc(applicationNode, manifestNode),
        Arc(applicationNode, uiNode),
        Arc(applicationNode, thirdPartyLibrariesNode),
        Arc(applicationNode, concurrencyNode),
        Arc(applicationNode, testingNode),
        Arc(architectureNode, architectureComponentsNode),
        Arc(architectureNode, diNode)
    )
}
