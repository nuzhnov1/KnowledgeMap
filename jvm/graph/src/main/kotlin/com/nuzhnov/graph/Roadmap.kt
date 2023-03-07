package com.nuzhnov.graph

@Suppress("unused", "MemberVisibilityCanBePrivate")
class Roadmap {

    private val _knowledges = mutableListOf<Knowledge>()
    private val matrix = mutableListOf(mutableListOf<Arc?>())

    val knowledges: Iterable<Knowledge> = _knowledges
    val arcs: Iterable<Arc> get() = matrix.flatten().filterNotNull()


    constructor(knowledges: Iterable<Knowledge>) {
        addKnowledge(knowledges)
    }

    constructor(vararg knowledges: Knowledge) {
        addKnowledge(*knowledges)
    }

    constructor(vararg arc: Arc) {
        addArcs(arc.asIterable())
    }


    // Auxiliary methods:

    private fun getKnowledgeIndex(knowledge: Knowledge) = _knowledges.indexOf(knowledge)

    // Vertex methods:

    operator fun contains(knowledge: Knowledge) = knowledge in _knowledges

    fun addKnowledge(knowledge: Knowledge) {
        if (knowledge !in _knowledges) {
            matrix.add(MutableList(_knowledges.size) { null })
            matrix.forEach { row -> row.add(null) }
            _knowledges.add(knowledge)
        }
    }

    fun addKnowledge(knowledges: Iterable<Knowledge>) = knowledges.forEach { addKnowledge(it) }
    fun addKnowledge(vararg knowledges: Knowledge) = knowledges.forEach { addKnowledge(it) }

    fun removeKnowledge(knowledge: Knowledge) {
        val knowledgeIndex = getKnowledgeIndex(knowledge)

        if (knowledgeIndex >= 0) {
            matrix.removeAt(knowledgeIndex)
            matrix.forEach { row -> row.removeAt(knowledgeIndex) }
            _knowledges.removeAt(knowledgeIndex)
        }
    }

    fun removeKnowledges(knowledges: Iterable<Knowledge>) = knowledges.forEach { removeKnowledge(it) }
    fun removeKnowledges(vararg knowledge: Knowledge) = knowledge.forEach { removeKnowledge(it) }

    fun clear() {
        matrix.clear()
        _knowledges.clear()
    }

    // Arc methods:

    operator fun get(startKnowledge: Knowledge, nextKnowledge: Knowledge) = matrix
        .getOrNull(getKnowledgeIndex(startKnowledge))
        ?.getOrNull(getKnowledgeIndex(nextKnowledge))

    fun getArcs(
        startingKnowledges: Iterable<Knowledge>,
        nextKnowledges: Iterable<Knowledge>
    ): Iterable<Arc> = startingKnowledges
        .zip(nextKnowledges)
        .mapNotNull { (outputVertex, inputVertex) -> get(outputVertex, inputVertex) }

    fun getAllOutputArcs(knowledge: Knowledge): Iterable<Arc>? = matrix
        .getOrNull(getKnowledgeIndex(knowledge))
        ?.filterNotNull()

    operator fun contains(pairKnowledges: Pair<Knowledge, Knowledge>) =
        get(startKnowledge = pairKnowledges.first, nextKnowledge = pairKnowledges.second) != null

    fun addArc(arc: Arc) {
        addKnowledge(arc.startingKnowledge)
        addKnowledge(arc.nextKnowledge)

        val startingKnowledgeIndex = getKnowledgeIndex(arc.startingKnowledge)
        val nextKnowledgeIndex = getKnowledgeIndex(arc.nextKnowledge)

        matrix[startingKnowledgeIndex][nextKnowledgeIndex] = arc
    }

    fun addArcs(arcs: Iterable<Arc>) = arcs.forEach { arc -> addArc(arc) }
    fun addArcs(vararg arc: Arc) = arc.forEach { addArc(it) }

    fun removeArc(arc: Arc) {
        val knowledgesPair = arc.startingKnowledge to arc.nextKnowledge

        if (contains(knowledgesPair)) {
            val startingKnowledgeIndex = getKnowledgeIndex(arc.startingKnowledge)
            val nextKnowledgeIndex = getKnowledgeIndex(arc.nextKnowledge)

            matrix[startingKnowledgeIndex][nextKnowledgeIndex] = null
        }
    }

    fun removeArcs(arcs: Iterable<Arc>) = arcs.forEach { arc -> removeArc(arc) }
    fun removeArcs(vararg arc: Arc) = arc.forEach { removeArc(it) }
    
    fun clear(knowledge: Knowledge) {
        if (contains(knowledge)) {
            val knowledgeIndex = getKnowledgeIndex(knowledge)

            matrix[knowledgeIndex].forEachIndexed { i, _ -> matrix[knowledgeIndex][i] = null }
            matrix.forEach { row -> row[knowledgeIndex] = null }
        }
    }
}
