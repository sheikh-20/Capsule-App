package com.application.capsuleapp.domain.usecase

import com.application.capsuleapp.data.repository.CategoryRepository
import com.application.capsuleapp.domain.common.Resource
import com.application.capsuleapp.domain.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CategoryUseCase {
    operator fun invoke(): Flow<Resource<Category>>
}

class GetCategoryInteractor @Inject constructor(private val repository: CategoryRepository): CategoryUseCase {
    override fun invoke(): Flow<Resource<Category>> = repository()
}