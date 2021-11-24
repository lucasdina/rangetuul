package com.gunnr.tuul.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.TextView
import com.gunnr.tuul.R
import com.gunnr.tuul.models.AppData
import com.gunnr.tuul.services.ui.RecyclerTouchListener
import com.gunnr.tuul.services.adapters.ClassifierListAdapter
import com.gunnr.tuul.services.adapters.SearchAdapter
import com.gunnr.tuul.services.threading.ioThread
import kotlinx.android.synthetic.main.fragment_classifiers.*


class ClassifiersFragment : Fragment() {

    var appData: AppData? = null
    private var classifierListAdapter: ClassifierListAdapter? = null
    private var suggestionAdapter: SearchAdapter? = null

    companion object {
        fun newInstance(): ClassifiersFragment {
            return ClassifiersFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        Log.e("here", "dont")
        menu!!.clear()
        inflater!!.inflate(R.menu.classifiers_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ioThread {
            appData = AppData(activity!!)
            classifierListAdapter = ClassifierListAdapter(activity!!, appData!!.Classifiers!!)
            activity!!.runOnUiThread {
                setUpSearch((appData as AppData), inflater, (activity as AppCompatActivity))
                (activity as AppCompatActivity).setSupportActionBar(activity!!.findViewById(R.id.classifiers_toolbar))
                setHasOptionsMenu(true)
                classifier_list.adapter = classifierListAdapter
                createClickListener(activity as AppCompatActivity)
                createSearchClickListener((activity as AppCompatActivity))
            }
        }
        return inflater.inflate(R.layout.fragment_classifiers, container, false)
    }

    private fun setUpSearch(appData: AppData, inflater: LayoutInflater, activity: AppCompatActivity) {
        suggestionAdapter = SearchAdapter(inflater)
        suggestionAdapter!!.suggestions = appData.Classifiers
        searchBar.setCustomSuggestionAdapter(suggestionAdapter)
        searchBar.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                Log.d("LOG_TAG", javaClass.simpleName + " text changed " + charSequence)
                // send the entered text to our filter and let it manage everything
                suggestionAdapter!!.filter.filter(charSequence)
            }

            override fun afterTextChanged(editable: Editable) {

            }

        })

    }

    private fun createSearchClickListener(activity: AppCompatActivity) {
        val searchrv = activity.findViewById<RecyclerView>(R.id.mt_recycler)

        searchrv.addOnItemTouchListener(
            RecyclerTouchListener(
                activity.applicationContext,
                searchrv,
                object : RecyclerTouchListener.ClickListener {

                    override fun onClick(view: View, position: Int) {
                        navigateToPdf(view.findViewById<TextView>(R.id.search_item_subtitle).text.toString(), activity)
                    }

                    override fun onLongClick(view: View, position: Int) {

                    }
                })
        )
    }

    private fun createClickListener(activity: AppCompatActivity) {
        classifier_list.setOnItemClickListener { _, _, position, _ ->
            navigateToPdf(appData!!.Classifiers!![position].classifier!!, activity)
        }
    }

    private fun navigateToPdf(classifier: String, activity: AppCompatActivity) {
        activity.supportFragmentManager
            .beginTransaction()
            .add(R.id.container, ClassifierViewFragment.newInstance(classifier), "classifierViewActivity")
            .addToBackStack(null)
            .commit()

    }
}
