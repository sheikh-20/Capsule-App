package com.application.capsuleapp.data.repository

import com.application.capsuleapp.domain.common.Resource
import com.application.capsuleapp.domain.model.Category
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

interface CategoryRepository {
    operator fun invoke(): Flow<Resource<Category>>
}

class CategoryRepositoryImpl @Inject constructor(private val database: FirebaseDatabase): CategoryRepository {

    private companion object {
        const val TAG = "CategoryRepositoryImpl"
    }

    override fun invoke(): Flow<Resource<Category>> = flow {
        emit(Resource.Loading)

        try {
            val result = database.getReference("category").get().await()

            emit(Resource.Success(Category(result.value as List<String>) ?: return@flow))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }

    }.catch {
        Timber.tag(TAG).e(it)
        emit(Resource.Failure(it))
    }
}