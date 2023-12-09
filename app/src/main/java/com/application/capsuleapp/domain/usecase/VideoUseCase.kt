package com.application.capsuleapp.domain.usecase

import com.application.capsuleapp.data.repository.VideoRepository
import com.application.capsuleapp.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface VideoUseCase {
    operator fun invoke(category: String = ""): Flow<Resource<String>>
}

class GetVideoInteractor @Inject constructor(private val repository: VideoRepository): VideoUseCase {
    override fun invoke(category: String): Flow<Resource<String>> = repository(category)
}