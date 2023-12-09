package com.application.capsuleapp.domain.usecase

import com.application.capsuleapp.data.repository.QuizRepository
import com.application.capsuleapp.domain.common.Resource
import com.application.capsuleapp.domain.model.Quiz
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface QuizUseCase {
    operator fun invoke(category: String = ""): Flow<Resource<List<Quiz>>>
}

class GetQuizInteractor @Inject constructor(private val repository: QuizRepository): QuizUseCase {
    override fun invoke(category: String): Flow<Resource<List<Quiz>>> = repository(category)
}