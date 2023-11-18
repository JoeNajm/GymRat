package com.example.finalgymlog.data

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.finalgymlog.data.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionViewModel(application: Application) : AndroidViewModel(application) {
    val readAllSession: LiveData<List<Session>>
    private val repository: SessionRepository

    init {
        val sessionDao = SessionDatabase.getDatabase(application).sessionDao()
        repository = SessionRepository(sessionDao)
        readAllSession = repository.readAllsession
    }

    fun addSession(session: Session) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addSession(session)
        }
    }

    fun updateSession(session: Session) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSession(session)
        }
    }

    fun deleteSession(session: Session) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSession(session)
        }
    }
}


class ExoViewModel(application: Application) : AndroidViewModel(application) {
    val readAllExo: LiveData<List<Exo>>
    private val repository: ExoRepository

    init {
        val exoDao = ExoDatabase.getDatabase(application).exoDao()
        repository = ExoRepository(exoDao)
        readAllExo = repository.readAllexo
    }

    fun addExo(exo: Exo) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addExo(exo)
        }
    }

    fun readExoByParentId(parentId: Int): LiveData<List<Exo>> {
        return repository.readExoByParentId(parentId)
    }

    fun updateExo(exo: Exo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateExo(exo)
        }
    }

    fun deleteExo(exo: Exo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteExo(exo)
        }
    }
    fun deleteExoByParentId(parentId: Int){
        repository.deleteExoByParentId(parentId)
    }
}

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val currentExo = MutableLiveData<Exo>()
    private val currentSession = MutableLiveData<Session>()

    fun setCurrentExo(input: Exo) {
        currentExo.value = input
    }

    fun getCurrentExo(): LiveData<Exo> {
        return currentExo
    }

    fun setCurrentSession(input: Session) {
        currentSession.value = input
    }

    fun getCurrentSession(): LiveData<Session> {
        return currentSession
    }
}