package dev.mickablondo.missiondefis.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dev.mickablondo.missiondefis.databinding.ActivityChildSelectionBinding
import dev.mickablondo.missiondefis.model.Child
import dev.mickablondo.missiondefis.utils.loadSelectedChildId
import dev.mickablondo.missiondefis.utils.saveSelectedChildId

class ChildSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChildSelectionBinding
    private val viewModel: MissionViewModel by viewModels { MissionViewModelFactory(application) }
    private var childrenList = listOf<Child>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val savedChildId = loadSelectedChildId(this)
        if (savedChildId != null) {
            viewModel.children.observe(this) { children ->
                val savedChild = children.find { it.id == savedChildId }
                if (savedChild != null) {
                    openChallenges(savedChild)
                }
            }
        }

        viewModel.children.observe(this) { children ->
            childrenList = children
            val names = children.map { it.name }
            binding.listChildren.adapter = ChildListAdapter(names) { position ->
                val child = children[position]
                saveSelectedChildId(this, child.id)
                openChallenges(child)
            }
        }
    }

    private fun openChallenges(child: Child) {
        val intent = Intent(this, ChildChallengesActivity::class.java)
        intent.putExtra("childId", child.id)
        intent.putExtra("childName", child.name)
        startActivity(intent)
        finish()
    }
}
