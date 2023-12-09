package com.application.capsuleapp.di

import com.application.capsuleapp.data.repository.CategoryRepository
import com.application.capsuleapp.data.repository.CategoryRepositoryImpl
import com.application.capsuleapp.data.repository.NotesRepository
import com.application.capsuleapp.data.repository.NotesRepositoryImpl
import com.application.capsuleapp.data.repository.QuizRepository
import com.application.capsuleapp.data.repository.QuizRepositoryImpl
import com.application.capsuleapp.data.repository.SettingsPreferenceImpl
import com.application.capsuleapp.data.repository.SettingsPreferenceRepository
import com.application.capsuleapp.data.repository.VideoRepository
import com.application.capsuleapp.data.repository.VideoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesPreferenceRepositoryImpl(settingsPreferenceImpl: SettingsPreferenceImpl): SettingsPreferenceRepository

    @Binds
    abstract fun providesCategoryRepositoryImpl(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun providesQuizRepositoryImpl(quizRepositoryImpl: QuizRepositoryImpl): QuizRepository

    @Binds
    abstract fun providesNotesRepositoryImpl(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository

    @Binds
    abstract fun providesVideoRepositoryImpl(videoRepositoryImpl: VideoRepositoryImpl): VideoRepository
}
