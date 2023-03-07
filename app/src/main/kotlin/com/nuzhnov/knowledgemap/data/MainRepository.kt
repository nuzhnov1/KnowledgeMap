package com.nuzhnov.knowledgemap.data

import com.nuzhnov.knowledgemap.data.local.AndroidRoadmapSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
internal class MainRepository @Inject constructor(
    private val androidRoadmapSource: AndroidRoadmapSource
) {

    val roadmap get() = androidRoadmapSource.roadmap
}
