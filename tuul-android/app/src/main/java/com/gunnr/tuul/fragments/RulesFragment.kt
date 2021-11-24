package com.gunnr.tuul.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.*
import com.gunnr.tuul.R
import com.gunnr.tuul.services.threading.ioThread
import kotlinx.android.synthetic.main.fragment_pdf_viewer.*
import com.gunnr.tuul.services.misc.PDFUtil


class RulesFragment : Fragment() {

    companion object {
        fun newInstance(): RulesFragment {
            return RulesFragment()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if(item!!.itemId == R.id.open_rulebook_externally){
            val util = PDFUtil()
            util.open((activity as AppCompatActivity), "rulebook")
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        Log.e("here", "dont")
        menu!!.clear()
        inflater!!.inflate(R.menu.rules_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ioThread {
            activity!!.runOnUiThread {
                (activity as AppCompatActivity).setSupportActionBar(activity!!.findViewById(R.id.pdf_viewer_toolbar))
                setHasOptionsMenu(true)
                pdfView.fromAsset("pdfs/rulebook.pdf").load()
            }
        }
        return inflater.inflate(R.layout.fragment_pdf_viewer, container, false)
    }
}
