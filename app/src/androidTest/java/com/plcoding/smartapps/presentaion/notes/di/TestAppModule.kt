package com.gharibe.smartapps.di

import android.app.Application
import androidx.room.Room
import com.gharibe.smartapps.feature_note.data.data_source.NoteDataBase
import com.gharibe.smartapps.feature_note.data.repository.NoteRepositoryImpl
import com.gharibe.smartapps.feature_note.domain.repository.NoteRepository
import com.gharibe.smartapps.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
     @Provides
     @Singleton
     fun provideNoteDatabase(app: Application) : NoteDataBase {
         return Room.inMemoryDatabaseBuilder(
             app,
             NoteDataBase::class.java,
         ).build()
     }
      @Provides
      @Singleton
      fun provideNoteRepository(db: NoteDataBase) : NoteRepository {
            return NoteRepositoryImpl(db.noteDao)
      }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository) :NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository),
        )
    }
}