package dev.mickablondo.missiondefis.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.mickablondo.missiondefis.databinding.ActivityChildChallengesEditBinding
import kotlinx.coroutines.launch

class ChildChallengesEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChildChallengesEditBinding
    private val viewModel: MissionViewModel by viewModels { MissionViewModelFactory(application) }
    private var childId: Int = -1
    private var childName: String = ""

    companion object {
        fun start(context: Context, childId: Int, childName: String) {
            val intent = Intent(context, ChildChallengesEditActivity::class.java)
            intent.putExtra("childId", childId)
            intent.putExtra("childName", childName)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildChallengesEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        childId = intent.getIntExtra("childId", -1)
        childName = intent.getStringExtra("childName") ?: ""

        title = "Défis de $childName"

        val adapter = ChallengeAdapter { challenge, isChecked ->
            lifecycleScope.launch {
                viewModel.markChallengeDone(challenge.copy(completed = isChecked))
                Toast.makeText(this@ChildChallengesEditActivity, "Défi ${if (isChecked) "coché" else "décoché"}", Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.getChallengesForChild(childId)
        viewModel.challenges.observe(this) { list ->
            adapter.submitList(list)
        }

        binding.buttonAddChallenge.setOnClickListener {
            val title = binding.editTextChallengeTitle.text.toString().trim()
            val icon = binding.editTextChallengeIcon.text.toString().trim()
            if (title.isEmpty()) {
                Toast.makeText(this, "Le titre ne peut pas être vide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (icon.isEmpty()) {
                Toast.makeText(this, "L'icône ne peut pas être vide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                viewModel.addChallenge(childId, title, icon)
                binding.editTextChallengeTitle.text.clear()
                binding.editTextChallengeIcon.text.clear()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()  // Termine ParentScreenActivity pour revenir en arrière
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
