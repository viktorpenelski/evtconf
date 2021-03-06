package com.github.viktorpenelski.evtconf.spreadsheet.tournaments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.github.viktorpenelski.evtconf.R
import kotlinx.android.synthetic.main.tournament_entry_list_row.view.*


/**
 * Created by Vic on 2/25/2018.
 */
class TournamentEntryListAdapter(private var activity: Activity, private var entries: List<TournamentSpreadsheetEntry>)
    : BaseAdapter() {

    private class ViewHolder(row: View) {
        var txtTitle: TextView = row.txtTitle
        var imgLogo: SimpleDraweeView = row.imgLogo
        var linkView: LinearLayout = row.tournament_link_container
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.tournament_entry_list_row, parent, false)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        entries[position].let {
            if (it.title.isBlank()) {
                viewHolder.txtTitle.visibility = View.GONE
            } else {
                viewHolder.txtTitle.text = it.title
            }

            val linkView = viewHolder.linkView
            if (URLUtil.isNetworkUrl(it.redirectUrl) && linkView.visibility == View.INVISIBLE) {
                linkView.visibility = View.VISIBLE
            } else if (!URLUtil.isNetworkUrl(it.redirectUrl) && linkView.visibility == View.VISIBLE){
                viewHolder.linkView.visibility = View.INVISIBLE
            }

            if (it.picture.isNotBlank()) {
                viewHolder.imgLogo.setImageURI(Uri.parse(it.picture))
            } else {
                viewHolder.imgLogo.setActualImageResource(R.drawable.gplaytvlogo)
            }
        }

        return view as View
    }

    override fun getItem(i: Int): TournamentSpreadsheetEntry {
        return entries[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return entries.size
    }
}