package com.gharibe.smartapps.presentaion.add_edit_notes

import android.util.Log
import android.util.Log.DEBUG
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gharibe.smartapps.feature_note.domain.model.InvalidNoteException
import com.gharibe.smartapps.feature_note.domain.model.Note
import com.gharibe.smartapps.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCase: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter title..."
    ))
    val noteTitle : State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter content..."
    ))
    val noteContent : State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor : State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private var currentNoteId:Int? = null
    init {
        savedStateHandle.get<Int>("noteIde")?.let { noteId ->
            Log.d("Gharibe", "noteId $noteId")
            if (noteId != -1) {
                viewModelScope.launch {
                    notesUseCase.getNoteUseCase(noteId)?.also {
                        currentNoteId = it.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = it.title,
                            visible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = it.title,
                            visible = false
                        )
                    }
                }
            }
        }
    }
    fun onEvent(event :AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus->{
                _noteTitle.value = noteTitle.value.copy(
                    visible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus->{
                _noteContent.value = noteContent.value.copy(
                    visible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.newColor
            }
            is AddEditNoteEvent.SaveNote-> {
                viewModelScope.launch {
                    try {
                        notesUseCase.addNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timeStamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e: InvalidNoteException){
                        _eventFlow.emit(UiEvent.ShowSnackBar(
                            e.message?: "Couldnt save note"
                        ))
                    }
                }
            }
        }
    }
    sealed class UiEvent {
        data class ShowSnackBar(val message : String):UiEvent()
        object SaveNote:UiEvent()
    }
}