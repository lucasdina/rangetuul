package com.gunnr.tuul.services.adapters

import com.gunnr.tuul.models.Classifier
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gunnr.tuul.R
import android.widget.Filter


class SearchAdapter(inflater: LayoutInflater?) :
    SuggestionsAdapter<Classifier, SearchAdapter.SuggestionHolder>(inflater) {

    override fun getSingleViewHeight(): Int {
        return 80
    }

    override fun onBindSuggestionHolder(suggestion: Classifier?, holder: SuggestionHolder?, position: Int) {
        holder!!.title.text = suggestion!!.name
        holder.subtitle.text = suggestion.classifier
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SuggestionHolder {
        return SuggestionHolder(layoutInflater.inflate(R.layout.search_suggestion_item, p0, false))
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                val term = constraint.toString()
                if (term.isEmpty())
                    suggestions = suggestions_clone
                else {
                    suggestions = ArrayList()
                    for (item in suggestions_clone)
                        if (item.name!!.toLowerCase().contains(term.toLowerCase()) || item.classifier!!.toLowerCase().contains(term.toLowerCase()))
                            suggestions.add(item)
                }
                results.values = suggestions
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                suggestions = results.values as MutableList<Classifier>?
                notifyDataSetChanged()
            }
        }
    }

    class SuggestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.search_item_title)
        var subtitle: TextView = itemView.findViewById(R.id.search_item_subtitle)

    }
}