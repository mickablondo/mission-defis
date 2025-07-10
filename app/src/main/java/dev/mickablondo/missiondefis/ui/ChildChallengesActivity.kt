package dev.mickablondo.missiondefis.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        childId = intent.getIntExtra("childId", -1)
        childName = intent.getStringExtra("childName") ?: "l’enfant"

        title = "Défis de $childName"

        val adapter = ChallengeAdapter(
            onCheckChanged = { challenge, checked ->
                lifecycleScope.launch {
                    viewModel.markChallengeDone(challenge.copy(completed = checked))
                    viewModel.getChallengesForChild(childId) // relance la récupération
                }
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.getChallengesForChild(childId)
        viewModel.challenges.observe(this) { list ->
            adapter.submitList(list)

            val allDone = list.isNotEmpty() && list.all { it.completed }
            binding.buttonReward.isEnabled = allDone
            binding.buttonReward.visibility = if (allDone) View.VISIBLE else View.GONE

            Log.d("ChildChallengesActivity", "Challenges updated, allDone=$allDone, list=$list")
        }

        binding.buttonChangeChild.setOnClickListener {
            // Vide la sélection actuelle (optionnel, selon comment tu gères la sauvegarde)
            saveSelectedChildId(this, null)  // Ou une méthode pour effacer la sauvegarde

            // Lance l'activité de sélection des enfants
            val intent = Intent(this, ChildSelectionActivity::class.java)
            // Si tu veux éviter de revenir ici en back stack, tu peux ajouter des flags :
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("forceSelectChild", true)
            startActivity(intent)

            // Termine l'activité actuelle si tu veux qu'elle ne soit plus accessible au back press
            finish()
        }

        binding.buttonReward.setOnClickListener {
            RewardActivity.start(this, childId, childName)
            finish()
        }
    }

    private fun saveSelectedChildId(context: Context, childId: Int?) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().apply {
            if (childId == null) {
                remove("selected_child_id")
            } else {
                putInt("selected_child_id", childId)
            }
            apply()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish() // Ferme l'activité et revient à l'écran précédent
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
