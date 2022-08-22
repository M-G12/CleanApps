package com.plcoding.smartapps.feature_note.domain.use_case

import com.gharibe.smartapps.feature_note.domain.model.InvalidNoteException
import com.gharibe.smartapps.feature_note.domain.model.Note
import com.gharibe.smartapps.feature_note.domain.repository.NoteRepository
import com.gharibe.smartapps.feature_note.domain.use_case.AddNoteUseCase
import com.plcoding.smartapps.feature_note.data.repository.FakeNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteUseCaseTest {
    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var repository: NoteRepository

    @Before
    fun setup() {
        repository = FakeNoteRepository()
        addNoteUseCase = AddNoteUseCase(repository)

    }

    @Test(expected = InvalidNoteException::class)
    fun `Add note, throw an error if title is blank`() {
        runBlocking {
            addNoteUseCase.invoke(
                Note(
                    title = "",
                    content = "note note",
                    timeStamp = 100L,
                    color = 1
                )
            )
        }
    }

    @Test(expected = InvalidNoteException::class)
    fun `Add note, throw an error if content is blank`() {
        runBlocking {
            addNoteUseCase.invoke(
                Note(
                    title = "Note",
                    content = "",
                    timeStamp = 100L,
                    color = 1
                )
            )
        }
    }

    @Test
    fun `Add note, successfully`() {
        runBlocking {
            addNoteUseCase.invoke(
                Note(
                    title = "Note",
                    content = "Note",
                    timeStamp = 100L,
                    color = 1
                )
            )
        }
    }
}
