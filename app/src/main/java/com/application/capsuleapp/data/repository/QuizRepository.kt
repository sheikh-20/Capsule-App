package com.application.capsuleapp.data.repository

import com.application.capsuleapp.domain.common.Resource
import com.application.capsuleapp.domain.model.Category
import com.application.capsuleapp.domain.model.Quiz
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

interface QuizRepository {
    operator fun invoke(category: String = ""): Flow<Resource<List<Quiz>>>
}

class QuizRepositoryImpl @Inject constructor(private val database: FirebaseDatabase): QuizRepository {

    private companion object {
        const val TAG = "QuizRepositoryImpl"
    }

    override fun invoke(category: String): Flow<Resource<List<Quiz>>> = flow {
        emit(Resource.Loading)

        try {
            val result = database.getReference("quiz").child(category).get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d(json)

            val itemType = object : TypeToken<List<Quiz>>() {}.type
            emit(Resource.Success(Gson().fromJson<List<Quiz>>(json, itemType)?: return@flow))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }.catch {
        Timber.tag(TAG).e(it)
        emit(Resource.Failure(it))
    }
}

