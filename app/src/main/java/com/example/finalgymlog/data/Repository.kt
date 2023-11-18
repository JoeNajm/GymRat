package com.example.finalgymlog.data

import androidx.lifecycle.LiveData
import com.example.finalgymlog.data.Session

class SessionRepository(private val sessionDao: SessionDao) {
    val readAllsession:  LiveData<List<Session>> = sessionDao.readAllSession()

    suspend fun addSession(session: Session) {
        sessionDao.addSession(session)
    }

    suspend fun updateSession(session: Session) {
        sessionDao.updateSession(session)
    }

    suspend fun deleteSession(session: Session){
        sessionDao.deleteSession(session)
    }
}

class ExoRepository(private val exoDao: ExoDao) {
    val readAllexo:  LiveData<List<Exo>> = exoDao.readAllExo()

    suspend fun addExo(exo: Exo) {
        exoDao.addExo(exo)
    }

    fun readExoByParentId(parentId: Int): LiveData<List<Exo>> {
        return exoDao.readExoByParentId(parentId)
    }

    suspend fun updateExo(exo: Exo) {
        exoDao.updateExo(exo)
    }

    suspend fun deleteExo(exo: Exo){
        exoDao.deleteExo(exo)
    }

    fun deleteExoByParentId(parentId: Int){
        exoDao.deleteExoByParentId(parentId)
    }
}