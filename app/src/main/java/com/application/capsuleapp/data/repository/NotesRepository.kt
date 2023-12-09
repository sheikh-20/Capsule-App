package com.application.capsuleapp.data.repository

import com.application.capsuleapp.domain.common.Resource
import com.application.capsuleapp.domain.model.Category
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

interface NotesRepository {
    operator fun invoke(category: String = ""): Flow<Resource<String>>
}

class NotesRepositoryImpl @Inject constructor(private val database: FirebaseDatabase): NotesRepository {

    private companion object {
        const val TAG = "NotesRepositoryImpl"
    }

    override fun invoke(category: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading)

        try {
            val result = database.getReference("notes").child(category).get().await()

            Timber.tag(TAG).d(result.value.toString())
            emit(Resource.Success(result.value.toString() ?: return@flow))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }

    }.catch {
        Timber.tag(TAG).e(it)
        emit(Resource.Failure(it))
    }
}