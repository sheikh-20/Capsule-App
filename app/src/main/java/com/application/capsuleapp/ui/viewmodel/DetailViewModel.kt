package com.application.capsuleapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.capsuleapp.domain.model.Quiz
import com.application.capsuleapp.domain.common.Resource
import com.application.capsuleapp.domain.model.Category
import com.application.capsuleapp.domain.usecase.NotesUseCase
import com.application.capsuleapp.domain.usecase.QuizUseCase
import com.application.capsuleapp.domain.usecase.VideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUIState(
    val quizIndex: Int = 0,
    val showFinalScoreDialog: Boolean = false,
    val score: Int = 0
)
@HiltViewModel
class DetailViewModel @Inject constructor(private val quizUseCase: QuizUseCase,
                                          private val notesUseCase: NotesUseCase,
                                          private val videoUseCase: VideoUseCase): ViewModel() {

    private var _quiz = MutableStateFlow<Resource<List<Quiz>>>(Resource.Loading)
    val quiz: StateFlow<Resource<List<Quiz>>> get() = _quiz

    private var _notes = MutableStateFlow<Resource<String>>(Resource.Loading)
    val notes: StateFlow<Resource<String>> get() = _notes

    private var _video = MutableStateFlow<Resource<String>>(Resource.Loading)
    val video: StateFlow<Resource<String>> get() = _video

    private var _detailUIState = MutableStateFlow(DetailUIState())
    val detailUIState: StateFlow<DetailUIState> get() = _detailUIState


    fun getQuestion(category: String = "") = viewModelScope.launch {
        quizUseCase(category).collectLatest {
            _quiz.value = it
        }
    }

    fun getNotes(category: String = "") = viewModelScope.launch {
        notesUseCase(category).collectLatest {
            _notes.value = it
        }
    }

    fun getVideo(category: String = "") = viewModelScope.launch {
        videoUseCase(category).collectLatest {
            _video.value = it
        }
    }

    fun showNextQuiz(quizList: List<Quiz>, currentQuiz: Quiz, selectedOption: String,) {
        if (detailUIState.value.quizIndex < quizList.size.dec()) {
            _detailUIState.update {
                it.copy(
                    quizIndex = it.quizIndex.inc(),
                    score = if (checkCorrectAnswer(currentQuiz = currentQuiz, selectedOption = selectedOption)) it.score.plus(10) else it.score
                    )
            }
        } else {
            _detailUIState.update {
                it.copy(showFinalScoreDialog = it.showFinalScoreDialog.not())
            }
        }
    }

    fun checkCorrectAnswer(currentQuiz: Quiz, selectedOption: String, showSnackBar: (String) -> Unit = { }): Boolean {
        return if (currentQuiz.ans == selectedOption) {
            showSnackBar("Correct Answer!")
            true
        } else {
            showSnackBar("Answer is incorrect!")
            false
        }
    }

    fun onDismiss() {
        _detailUIState.update {
            it.copy(showFinalScoreDialog = false)
        }
    }

    fun onReset() {
        _detailUIState.update {
            it.copy(quizIndex = 0, showFinalScoreDialog = false)
        }
    }
}