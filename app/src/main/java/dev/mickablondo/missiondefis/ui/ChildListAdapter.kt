package dev.mickablondo.missiondefis.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ChildListAdapter(
    private val items: List<String>,
    private val onClick: (position: Int) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = items.size
    override fun getItem(position: Int): String = items[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = convertView ?: LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        val tv = v.findViewById<TextView>(android.R.id.text1)
        tv.text = items[position]
        v.setOnClickListener { onClick(position) }
        return v
    }
}
