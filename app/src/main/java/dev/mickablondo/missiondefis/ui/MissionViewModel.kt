package dev.mickablondo.missiondefis.ui

import android.app.Application
import androidx.lifecycle.*
import dev.mickablondo.missiondefis.data.MissionDatabase
import dev.mickablondo.missiondefis.model.Child
import dev.mickablondo.missiondefis.model.Challenge
import kotlinx.coroutines.launch

class MissionViewModel(application: Application) : AndroidViewModel(application) {
    private val db = MissionDatabase.getDatabase(application)
    val children: LiveData<List<Child>> = db.childDao().getAll().asLiveData()

    private val _challenges = MutableLiveData<List<Challenge>>()
    val challenges: LiveData<List<Challenge>> = _challenges

    fun getChallengesForChild(childId: Int) {
        viewModelScope.launch {
            db.challengeDao().getByChild(childId).collect {
                _challenges.postValue(it)
            }
        }
    }

    fun addChild(name: String) = viewModelScope.launch {
        db.childDao().insert(Child(name = name))
    }

    fun addChallenge(childId: Int, title: String, icon: String) = viewModelScope.launch {
        db.challengeDao().insert(Challenge(childId = childId, title = title, icon = icon))
    }

    fun markChallengeDone(challenge: Challenge) = viewModelScope.launch {
        db.challengeDao().update(challenge)
    }

    fun resetChallenges(childId: Int) = viewModelScope.launch {
        db.challengeDao().deleteByChild(childId)
    }
}
