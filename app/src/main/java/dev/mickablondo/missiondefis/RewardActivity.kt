package dev.mickablondo.missiondefis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.mickablondo.missiondefis.databinding.ActivityRewardBinding

class RewardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRewardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val childName = intent.getStringExtra("childName") ?: "l'enfant"
        binding.textView.text = getString(R.string.reward_message, childName)
    }

    companion object {
        fun start(context: Context, childName: String) {
            val intent = Intent(context, RewardActivity::class.java)
            intent.putExtra("childName", childName)
            context.startActivity(intent)
        }
    }
}