package dev.mickablondo.missiondefis.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.mickablondo.missiondefis.databinding.ActivityRoleSelectionBinding

class RoleSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoleSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonParent.setOnClickListener {
            startActivity(Intent(this, ParentScreenActivity::class.java))
        }

        binding.buttonChild.setOnClickListener {
            startActivity(Intent(this, ChildSelectionActivity::class.java))
        }
    }
}