package com.gunnr.tuul.services.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gunnr.tuul.R
import com.gunnr.tuul.models.Classifier
import android.graphics.drawable.Drawable
import android.util.Log
import java.io.IOException


class ClassifierListAdapter(private val context: Context, private val classifierList: List<Classifier>) : BaseAdapter() {

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return classifierList.size
    }

    override fun getItem(position: Int): Any {
        return classifierList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.classifier_list_item, null, true)

            holder.cName = convertView.findViewById(R.id.classifier_name) as TextView
            holder.cId = convertView.findViewById(R.id.classifier_id) as TextView
            holder.cScoring = convertView.findViewById(R.id.classifier_scoring) as TextView
            holder.cRounds = convertView.findViewById(R.id.classifier_rounds) as TextView
            holder.cThumb = convertView.findViewById(R.id.classifier_thumb) as ImageView

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.cName!!.text = classifierList[position].name
        holder.cId!!.text = classifierList[position].classifier
        holder.cScoring!!.text = "Scoring: ${classifierList[position].scoring}"
        holder.cRounds!!.text = "Rounds: ${classifierList[position].rounds.toString()}"

        try {
            // get input stream
            val ims = convertView!!.context.assets.open("thumbs/${classifierList[position].classifier}.png")
            // load image as Drawable
            val d = Drawable.createFromStream(ims, null)
            // set image to ImageView
            holder.cThumb!!.setImageDrawable(d)
        } catch (ex: IOException) {
            Log.e("classifierListAdapter", ex.toString())
        }


        return convertView!!
    }

    private inner class ViewHolder {

        var cName: TextView? = null
        var cId: TextView? = null
        var cScoring: TextView? = null
        var cRounds: TextView? = null
        var cThumb: ImageView? = null

    }

}