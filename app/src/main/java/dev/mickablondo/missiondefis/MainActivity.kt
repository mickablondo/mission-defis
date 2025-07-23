package dev.mickablondo.missiondefis

import android.content.Intent
import android.widget.TextView
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import dev.mickablondo.missiondefis.databinding.ActivityMainBinding
import dev.mickablondo.missiondefis.ui.RoleSelectionActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val copyrightText = findViewById<TextView>(R.id.textCopyright)
        // Parse le texte HTML pour que les <a> soient détectés comme liens
        copyrightText.text = Html.fromHtml(getString(R.string.copyright_text), Html.FROM_HTML_MODE_LEGACY)
        copyrightText.movementMethod = LinkMovementMethod.getInstance()

        binding.buttonStart.setOnClickListener {
            val intent = Intent(this, RoleSelectionActivity::class.java)
            startActivity(intent)
        }
    }
}