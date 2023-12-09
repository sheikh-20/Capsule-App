package com.application.capsuleapp.di

import com.application.capsuleapp.data.repository.CategoryRepository
import com.application.capsuleapp.data.repository.NotesRepository
import com.application.capsuleapp.data.repository.QuizRepository
import com.application.capsuleapp.data.repository.SettingsPreferenceRepository
import com.application.capsuleapp.data.repository.VideoRepository
import com.application.capsuleapp.domain.usecase.CategoryUseCase
import com.application.capsuleapp.domain.usecase.GetCategoryInteractor
import com.application.capsuleapp.domain.usecase.GetNotesInteractor
import com.application.capsuleapp.domain.usecase.GetQuizInteractor
import com.application.capsuleapp.domain.usecase.GetSettingsPreferenceInteractor
import com.application.capsuleapp.domain.usecase.GetVideoInteractor
import com.application.capsuleapp.domain.usecase.NotesUseCase
import com.application.capsuleapp.domain.usecase.QuizUseCase
import com.application.capsuleapp.domain.usecase.SettingsPreferenceUseCase
import com.application.capsuleapp.domain.usecase.VideoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providesUseCase(repository: SettingsPreferenceRepository): SettingsPreferenceUseCase {
        return GetSettingsPreferenceInteractor(repository)
    }

    @Provides
    @Singleton
    fun providesCategoryUseCase(repository: CategoryRepository): CategoryUseCase {
        return GetCategoryInteractor(repository)
    }

    @Provides
    @Singleton
    fun providesQuizUseCase(repository: QuizRepository): QuizUseCase {
        return GetQuizInteractor(repository)
    }

    @Provides
    @Singleton
    fun providesNotesUseCase(repository: NotesRepository): NotesUseCase {
        return GetNotesInteractor(repository)
    }

    @Provides
    @Singleton
    fun providesVideoUseCase(repository: VideoRepository): VideoUseCase {
        return GetVideoInteractor(repository)
    }
}