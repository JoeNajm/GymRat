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

    fun getDateFromParentID(parentId: Int): String {
        return sessionDao.getDateFromParentID(parentId)
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

    fun getAllInstances(exo_name: String?): LiveData<List<Exo>> {
        println("Getting all instances of $exo_name")
        return exoDao.getAllInstances(exo_name)
    }
}

class FoodRepository(private val foodDao: FoodDao) {
    val readAllfood:  LiveData<List<Food>> = foodDao.readAllFood()

    suspend fun addFood(food: Food) {
        foodDao.addFood(food)
    }

    fun readFoodByParentId(parentId: Int): LiveData<List<Food>> {
        return foodDao.readFoodByParentId(parentId)
    }

    suspend fun updateFood(food: Food) {
        foodDao.updateFood(food)
    }

    suspend fun deleteFood(food: Food){
        foodDao.deleteFood(food)
    }

    fun deleteFoodByParentId(parentId: Int){
        foodDao.deleteFoodByParentId(parentId)
    }
}

class FridgeFoodRepository(private val fridgefoodDao: FridgeFoodDao) {
    val readAllfridgefood:  LiveData<List<FridgeFood>> = fridgefoodDao.readAllFridgeFood()

    suspend fun addFridgeFood(fridgefood: FridgeFood) {
        fridgefoodDao.addFridgeFood(fridgefood)
    }

    suspend fun updateFridgeFood(fridgefood: FridgeFood) {
        fridgefoodDao.updateFridgeFood(fridgefood)
    }

    suspend fun deleteFridgeFood(fridgefood: FridgeFood){
        fridgefoodDao.deleteFridgeFood(fridgefood)
    }
}


class ExoInventoryRepository(private val exoinventoryDao: ExoInventoryDao) {
    val readAllexoinventory:  LiveData<List<ExoInventory>> = exoinventoryDao.readAllExoInventory()

    suspend fun addExoInventory(exoinventory: ExoInventory) {
        exoinventoryDao.addExoInventory(exoinventory)
    }

    suspend fun updateExoInventory(exoinventory: ExoInventory) {
        exoinventoryDao.updateExoInventory(exoinventory)
    }

    suspend fun deleteExoInventory(exoinventory: ExoInventory){
        exoinventoryDao.deleteExoInventory(exoinventory)
    }

    fun readExoByType(type: String): LiveData<List<ExoInventory>> {
        return exoinventoryDao.readExoByType(type)
    }
}