package com.plcoding.smartapps.feature_note.domain.use_case

import com.gharibe.smartapps.feature_note.domain.model.Note
import com.gharibe.smartapps.feature_note.domain.use_case.DeleteNoteUseCase
import com.gharibe.smartapps.feature_note.domain.use_case.GetNotesUseCase
import com.plcoding.smartapps.feature_note.data.repository.FakeNoteRepository
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import org.junit.Before
import org.junit.Test

class DeleteNoteUseCaseTest {
    private lateinit var deleteNotesUseCase: DeleteNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var notesToInsert: MutableList<Note>
    private lateinit var getNotesUseCase: GetNotesUseCase


    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNotesUseCase = GetNotesUseCase(fakeNoteRepository)
        deleteNotesUseCase = DeleteNoteUseCase(fakeNoteRepository)
        notesToInsert = mutableListOf()
        ('a'..'r').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timeStamp = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach {
                fakeNoteRepository.insertNote(it)
            }
        }
    }

    @Test
    fun `Delete a note, and check if its actually deleted`() {
        val noteToDelete = notesToInsert.last()
        runBlocking {
            deleteNotesUseCase.invoke(noteToDelete)
            val notes = getNotesUseCase.invoke()
            assertThat(noteToDelete).isNotIn(notes.toList())
        }

    }
}

