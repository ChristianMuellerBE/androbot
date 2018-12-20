package ch.bfh.christianmueller.mymemory.game

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ch.bfh.christianmueller.mymemory.R

class MyListAdapter(context: Context, @LayoutRes itemLayoutRes: Int, items: List<MyListItem>) :
    ArrayAdapter<MyListItem>(context, itemLayoutRes, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val item = getItem(position)
        view.findViewById<TextView>(R.id.tv_listitem).text = item.text
        return view
    }
}

data class MyListItem(val text: String)
