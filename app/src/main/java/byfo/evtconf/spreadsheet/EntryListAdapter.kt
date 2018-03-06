package byfo.evtconf.spreadsheet

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import byfo.evtconf.R
import com.facebook.drawee.view.SimpleDraweeView


/**
 * Created by Vic on 2/25/2018.
 */
class EntryListAdapter(private var activity: Activity, private var entries: List<Entry>)
    : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtTime: TextView? = row?.findViewById<TextView>(R.id.txtTime)
        var txtTitle: TextView? = row?.findViewById<TextView>(R.id.txtTitle)
        var imgLogo: SimpleDraweeView? = row?.findViewById<View>(R.id.imgLogo) as SimpleDraweeView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.entry_list_row, parent, false)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        entries[position].let {
            viewHolder.txtTime?.text = it.time
            viewHolder.txtTitle?.text = it.title

            if (it.picture.isNotBlank()) {
                viewHolder.imgLogo?.setImageURI(Uri.parse(it.picture))
            } else {
                viewHolder.imgLogo?.setActualImageResource(R.drawable.gplaytvlogo)
            }
        }

        return view as View
    }

    override fun getItem(i: Int): Entry {
        return entries[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return entries.size
    }


}