package dev.mickablondo.missiondefis.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import dev.mickablondo.missiondefis.R
import dev.mickablondo.missiondefis.model.Child

class ChildAdapterWithDelete(
    private val children: List<Child>,
    private val onDeleteClick: (Child) -> Unit,
    private val onItemClick: (Child) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = children.size

    override fun getItem(position: Int): Any = children[position]

    override fun getItemId(position: Int): Long = children[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.item_child_with_delete, parent, false)

        val child = children[position]

        val nameView = view.findViewById<TextView>(R.id.childName)
        val deleteButton = view.findViewById<ImageButton>(R.id.deleteButton)

        nameView.text = child.name

        // Clique sur la ligne ouvre les défis
        view.setOnClickListener {
            onItemClick(child)
        }

        // Clique sur la croix supprime l'enfant
        deleteButton.setOnClickListener {
            onDeleteClick(child)
        }

        return view
    }
}
