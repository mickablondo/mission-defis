package dev.mickablondo.missiondefis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dev.mickablondo.missiondefis.databinding.ActivityRewardBinding
import dev.mickablondo.missiondefis.ui.ChildChallengesActivity

class RewardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRewardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val childName = intent.getStringExtra("childName") ?: "l'enfant"
        val childId = intent.getIntExtra("childId", -1)
        binding.textView.text = getString(R.string.reward_message, childName)

        binding.buttonBackToChallenges.setOnClickListener {
            val intent = Intent(this, ChildChallengesActivity::class.java)
            intent.putExtra("childId", childId)
            intent.putExtra("childName", childName)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        fun start(context: Context, childId: Int, childName: String) {
            val intent = Intent(context, RewardActivity::class.java)
            intent.putExtra("childId", childId)
            intent.putExtra("childName", childName)
            context.startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}