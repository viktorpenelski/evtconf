package com.github.viktorpenelski.evtconf.spreadsheet.mainstage

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.github.viktorpenelski.evtconf.R


/**
 * Created by Vic on 2/25/2018.
 */
class MainStageEntryListAdapter(private var activity: Activity, private var mainStageSpreadsheetEntries: List<MainStageSpreadsheetEntry>)
    : BaseAdapter() {

    private class ViewHolder(row: View) {
        var txtTime = row.findViewById(R.id.txtTime) as TextView
        var txtTitle = row.findViewById(R.id.txtTitle) as TextView
        var imgLogo = row.findViewById<View>(R.id.imgLogo) as SimpleDraweeView
        var imgLink = row.findViewById<View>(R.id.main_stage_img_link) as ImageView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.main_stage_entry_list_row, parent, false)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        mainStageSpreadsheetEntries[position].let {
            viewHolder.txtTime.text = it.time
            viewHolder.txtTitle.text = it.title

            val imgLink = viewHolder.imgLink
            if (URLUtil.isNetworkUrl(it.redirectUrl) && imgLink.visibility == View.INVISIBLE) {
                imgLink.visibility = View.VISIBLE
            } else if (!URLUtil.isNetworkUrl(it.redirectUrl) && imgLink.visibility == View.VISIBLE){
                viewHolder.imgLink.visibility = View.INVISIBLE
            }

            if (URLUtil.isNetworkUrl(it.picture)) {
                viewHolder.imgLogo.setImageURI(Uri.parse(it.picture))
            } else {
                viewHolder.imgLogo.setActualImageResource(R.drawable.gplaytvlogo)
            }

        }

        return view as View
    }

    override fun getItem(i: Int): MainStageSpreadsheetEntry {
        return mainStageSpreadsheetEntries[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return mainStageSpreadsheetEntries.size
    }

}