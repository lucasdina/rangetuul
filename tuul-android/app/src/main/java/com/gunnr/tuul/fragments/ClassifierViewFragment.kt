package com.gunnr.tuul.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.*
import com.gunnr.tuul.R
import com.gunnr.tuul.services.misc.PDFUtil
import com.gunnr.tuul.services.threading.ioThread
import kotlinx.android.synthetic.main.fragment_pdf_viewer.*

class ClassifierViewFragment : Fragment() {
    private var currentClassifier: String? = null

    companion object {
        fun newInstance(classifier: String) = ClassifierViewFragment().apply {
            arguments = Bundle().apply {
                putString("classifier", classifier)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if(item!!.itemId == R.id.open_classifier_externally){
            val util = PDFUtil()
            util.open((activity as AppCompatActivity), (currentClassifier as String))
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        Log.e("HEY THERE!", "Why are you looking at my app?")
        menu!!.clear()
        inflater!!.inflate(R.menu.classifier_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ioThread {
            activity!!.runOnUiThread {
                currentClassifier = arguments?.getString("classifier")
                (activity as AppCompatActivity).setSupportActionBar(activity!!.findViewById(R.id.pdf_viewer_toolbar))
                setHasOptionsMenu(true)
                pdfView.fromAsset("pdfs/$currentClassifier.pdf").load()
            }
        }
        return inflater.inflate(R.layout.fragment_pdf_viewer, container, false)
    }
}
