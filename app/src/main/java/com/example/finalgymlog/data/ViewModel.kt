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

    fun getDateFromParentID(parentId: Int): String {
        return repository.getDateFromParentID(parentId)
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

    fun getAllInstances(exo_name: String): LiveData<List<Exo>> {
        return repository.getAllInstances(exo_name)
    }
}

class FoodViewModel(application: Application) : AndroidViewModel(application) {
    val readAllFood: LiveData<List<Food>>
    private val repository: FoodRepository

    init {
        val foodDao = FoodDatabase.getDatabase(application).foodDao()
        repository = FoodRepository(foodDao)
        readAllFood = repository.readAllfood
    }

    fun addFood(food: Food) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addFood(food)
        }
    }

    fun readFoodByParentId(parentId: Int): LiveData<List<Food>> {
        return repository.readFoodByParentId(parentId)
    }

    fun updateFood(food: Food) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFood(food)
        }
    }

    fun deleteFood(food: Food) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFood(food)
        }
    }
    fun deleteFoodByParentId(parentId: Int){
        repository.deleteFoodByParentId(parentId)
    }
}

class FridgeFoodViewModel(application: Application) : AndroidViewModel(application) {
    val readAllFridgeFood: LiveData<List<FridgeFood>>
    private val repository: FridgeFoodRepository

    init {
        val fridgefoodDao = FridgeFoodDatabase.getDatabase(application).fridgefoodDao()
        repository = FridgeFoodRepository(fridgefoodDao)
        readAllFridgeFood = repository.readAllfridgefood
    }

    fun addFridgeFood(fridgefood: FridgeFood) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addFridgeFood(fridgefood)
        }
    }

    fun updateFridgeFood(fridgefood: FridgeFood) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFridgeFood(fridgefood)
        }
    }

    fun deleteFridgeFood(fridgefood: FridgeFood) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFridgeFood(fridgefood)
        }
    }
}

class ExoInventoryViewModel(application: Application) : AndroidViewModel(application) {
    val readAllExoInventory: LiveData<List<ExoInventory>>
    private val repository: ExoInventoryRepository

    init {
        val exoinventoryDao = ExoInventoryDatabase.getDatabase(application).exoinventoryDao()
        repository = ExoInventoryRepository(exoinventoryDao)
        readAllExoInventory = repository.readAllexoinventory
    }

    fun addExoInventory(exoinventory: ExoInventory) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addExoInventory(exoinventory)
        }
    }

    fun updateExoInventory(exoinventory: ExoInventory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateExoInventory(exoinventory)
        }
    }

    fun deleteExoInventory(exoinventory: ExoInventory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteExoInventory(exoinventory)
        }
    }

    fun readExoByType(type: String): LiveData<List<ExoInventory>> {
        return repository.readExoByType(type)
    }
}

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val currentExo = MutableLiveData<Exo>()
    private val currentSession = MutableLiveData<Session>()
    private val currentFridgeFood = MutableLiveData<FridgeFood>()
    private val currentExoInventory = MutableLiveData<ExoInventory>()
    private val exoInventoryList = MutableLiveData<List<ExoInventory>>()

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

    fun setCurrentFridgeFood(input: FridgeFood) {
        currentFridgeFood.value = input
    }

    fun getCurrentFridgeFood(): LiveData<FridgeFood> {
        return currentFridgeFood
    }

    fun setCurrentExoInventory(input: ExoInventory) {
        currentExoInventory.value = input
    }

    fun getCurrentExoInventory(): LiveData<ExoInventory> {
        return currentExoInventory
    }

    fun setCurrentExoInventoryList(input: List<ExoInventory>) {
        exoInventoryList.value = input
    }

    fun getCurrentExoInventoryList(): LiveData<List<ExoInventory>> {
        return exoInventoryList
    }
}