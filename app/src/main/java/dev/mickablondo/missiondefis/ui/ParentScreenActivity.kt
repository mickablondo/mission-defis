package dev.mickablondo.missiondefis.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dev.mickablondo.missiondefis.databinding.ActivityParentScreenBinding
import dev.mickablondo.missiondefis.model.Child
import kotlinx.coroutines.launch

class ParentScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParentScreenBinding
    private val viewModel: MissionViewModel by viewModels { MissionViewModelFactory(application) }

    private val childrenList = mutableListOf<Child>()

    private lateinit var adapter: ChildAdapterWithDelete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = ChildAdapterWithDelete(
            childrenList,
            onDeleteClick = { child ->
                // Affiche une boîte de confirmation avant suppression
                AlertDialog.Builder(this)
                    .setTitle("Supprimer enfant")
                    .setMessage("Voulez-vous vraiment supprimer ${child.name} ?")
                    .setPositiveButton("Oui") { _, _ ->
                        lifecycleScope.launch {
                            viewModel.deleteChild(child)
                        }
                    }
                    .setNegativeButton("Non", null)
                    .show()
            },
            onItemClick = { child ->
                ChildChallengesEditActivity.start(this, child.id, child.name)
            }
        )
        binding.listChildren.adapter = adapter

        viewModel.children.observe(this) { children ->
            childrenList.clear()
            childrenList.addAll(children)
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
