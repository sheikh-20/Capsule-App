package com.application.capsuleapp.domain.usecase

import com.application.capsuleapp.data.repository.NotesRepository
import com.application.capsuleapp.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NotesUseCase {
    operator fun invoke(category: String = ""): Flow<Resource<String>>
}

class GetNotesInteractor @Inject constructor(private val repository: NotesRepository): NotesUseCase {
    override fun invoke(category: String): Flow<Resource<String>> = repository(category)
}