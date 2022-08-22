package com.gharibe.smartapps.presentaion.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gharibe.smartapps.feature_note.domain.model.Note
import com.gharibe.smartapps.feature_note.domain.use_case.NoteUseCases
import com.gharibe.smartapps.feature_note.domain.util.NoteOrder
import com.gharibe.smartapps.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
        ) : ViewModel() {
    private val _state = mutableStateOf(NotState())
    val state : State<NotState> = _state
    private var recentlyDeletedNote: Note? = null
    private var getNotesJob : Job? = null
    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }
            fun onEvent(events: NotesEvents) {
                when(events) {
                    is NotesEvents.Order-> {
                        if(state.value.noteOrder::class == events.noteOrder::class &&
                            state.value.noteOrder.orderType == events.noteOrder.orderType
                        ) {
                            return
                        }
                        getNotes(noteOrder = events.noteOrder)
                    }
                    is NotesEvents.DeleteNote-> {
                        viewModelScope.launch {
                            noteUseCases.deleteNoteUseCase(events.note)
                            recentlyDeletedNote = events.note

                        }
                    }
                    is NotesEvents.RestoreNote-> {
                        viewModelScope.launch {
                            noteUseCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                            recentlyDeletedNote = null
                        }
                    }
                    is NotesEvents.ToggleOrderSection->{
                        _state.value = state.value.copy(
                            isOrderSectionVisible = !state.value.isOrderSectionVisible
                        )
                    }
                }
            }
    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase(noteOrder).onEach {
            _state.value = state.value.copy(
                notes = it,
                noteOrder = noteOrder
            )
        } .launchIn(viewModelScope)
    }
}