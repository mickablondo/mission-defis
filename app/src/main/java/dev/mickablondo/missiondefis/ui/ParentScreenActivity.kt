package dev.mickablondo.missiondefis.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dev.mickablondo.missiondefis.databinding.ActivityParentScreenBinding
import dev.mickablondo.missiondefis.model.Child
import kotlinx.coroutines.launch

class ParentScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParentScreenBinding
    private val viewModel: MissionViewModel by viewModels { MissionViewModelFactory(application) }

    private val childrenNames = mutableListOf<String>()
    private val childrenList = mutableListOf<Child>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Afficher la flèche retour dans la barre d’action
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, childrenNames)
        binding.listChildren.adapter = adapter

        viewModel.children.observe(this) { children ->
            childrenList.clear()
            childrenList.addAll(children)
            childrenNames.clear()
            childrenNames.addAll(children.map { it.name })
            adapter.notifyDataSetChanged()
        }

        binding.buttonAddChild.setOnClickListener {
            val newName = binding.editTextChildName.text.toString().trim()
            if (newName.isEmpty()) {
                Toast.makeText(this, "Le nom ne peut pas être vide", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    viewModel.addChild(newName)
                    binding.editTextChildName.text.clear()
                    Toast.makeText(this@ParentScreenActivity, "Enfant ajouté", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.listChildren.setOnItemClickListener { _, _, position, _ ->
            val child = childrenList[position]
            // Ouvre l'écran de gestion des défis pour cet enfant
            ChildChallengesEditActivity.start(this, child.id, child.name)
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
