package dev.mickablondo.missiondefis.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.mickablondo.missiondefis.RewardActivity
import dev.mickablondo.missiondefis.databinding.ActivityChildChallengesBinding
import kotlinx.coroutines.launch

class ChildChallengesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChildChallengesBinding
    private lateinit var childName: String
    private var childId: Int = -1

    private val viewModel: MissionViewModel by viewModels {
        MissionViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildChallengesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        childId = intent.getIntExtra("childId", -1)
        childName = intent.getStringExtra("childName") ?: "l’enfant"

        title = "Défis de $childName"

        val adapter = ChallengeAdapter(
            onCheckChanged = { challenge, checked ->
                lifecycleScope.launch {
                    viewModel.markChallengeDone(challenge.copy(completed = checked))
                }
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.getChallengesForChild(childId)
        viewModel.challenges.observe(this) { list ->
            adapter.submitList(list)
            if (list.isNotEmpty() && list.all { it.completed }) {
                // tous les défis sont cochés
                RewardActivity.start(this, childName)
                finish()
            }
        }
    }
}
