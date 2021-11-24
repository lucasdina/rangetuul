package com.gunnr.tuul.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import com.gunnr.tuul.R
import com.gunnr.tuul.Settings
import com.gunnr.tuul.models.AppData
import com.gunnr.tuul.models.Classifier
import com.gunnr.tuul.models.Division
import com.gunnr.tuul.models.HHF
import com.gunnr.tuul.services.threading.ioThread
import kotlinx.android.synthetic.main.fragment_calculator.*


class CalculatorFragment : Fragment() {
    private var appData: AppData? = null
    private var selectedClassifier: Classifier? = null
    private var selectedDivision: Division? = null
    private var enteredHf: Double? = 0.000000
    private var currentHHF: HHF? = null

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        println("cool breans")
        val intent = Intent(activity, Settings::class.java)
        intent.putExtra("SHOW_WELCOME", true)
        startActivity(intent)
        activity!!.finish()
        return true
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        return super.onContextItemSelected(item)
    }

    companion object {
        fun newInstance(): CalculatorFragment {
            return CalculatorFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        Log.e("here", "dont")
        menu!!.clear()
        inflater!!.inflate(R.menu.calculator_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ioThread {
            appData = AppData(activity!!)
            while (
                appData!!.Classifiers!!.isEmpty() ||
                appData!!.HHFS!!.isEmpty() ||
                appData!!.Divisions!!.isEmpty() ||
                appData!!.ClassRanges!!.isEmpty()
            ) {
                Thread.sleep(500)
                appData = AppData(context!!)
            }

            updateUi()
        }
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    private fun updateUi() {
        activity!!.runOnUiThread {
            (activity as AppCompatActivity).setSupportActionBar(activity!!.findViewById(R.id.calculator_toolbar))
            setHasOptionsMenu(true)
            initClassifierSpinner()
            initDivisionSpinner()
            initHHFInput()
        }
    }

    private fun initHHFInput() {
        hhfField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("Main", "edited")
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("Main", "edited")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                enteredHf = if(p0 == null || !p0.isNotBlank() || p0.toString() == "."){
                    0.00
                } else {
                    p0.toString().toDouble()
                }
                updateHHF()
            }
        })
    }

    private fun initClassifierSpinner() {
        val adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, appData!!.Classifiers!!)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        classifierSpinner.adapter = adapter

        classifierSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedClassifier = null
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedClassifier = appData!!.Classifiers!![position]
                updateHHF()
            }

        }
    }

    private fun initDivisionSpinner() {
        val adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, appData!!.Divisions!!)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        divisionSpinner.adapter = adapter

        divisionSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedDivision = null
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedDivision = appData!!.Divisions!![position]
                updateHHF()
            }
        }
    }

    private fun updateHHF() {
        if (selectedClassifier != null && selectedDivision != null && appData!!.Db != null) {
            currentHHF = appData!!.Db!!.HHFDao().getHHF(selectedClassifier!!.id, selectedDivision!!.id)
            val currentPercent = enteredHf!!.div(currentHHF!!.hhf!!)
            percent_value.text = "Score: ${String.format("%.2f", currentPercent * 100)}%"

            loadClassMins()

            hhf_value.text = "HHF: ${currentHHF!!.hhf}"
            letter_value.text = getClassificationLetter(currentPercent * 100)
        }
    }

    private fun getClassificationLetter(percentage: Double): CharSequence {
        if(percentage >= 0 && percentage < 40) {
            return 'D'.toString()
        }
        if(percentage >= 40 && percentage < 60) {
            return 'C'.toString()
        }
        if(percentage >= 60 && percentage < 75) {
            return 'B'.toString()
        }
        if(percentage >= 75 && percentage < 85) {
            return 'A'.toString()
        }
        if(percentage >= 85 && percentage < 95) {
            return 'M'.toString()
        }
        if(percentage >= 95) {
            return 'G'.toString()
        }
        return '?'.toString()
    }

    private fun loadClassMins(){
        d_percentage_text.text = String.format("D: %.2f", currentHHF!!.hhf!! * 0)
        c_percentage_text.text = String.format("C: %.2f", currentHHF!!.hhf!! * .40)
        b_percentage_text.text = String.format("B: %.2f", currentHHF!!.hhf!! * .60)
        a_percentage_text.text = String.format("A: %.2f", currentHHF!!.hhf!! * .75)
        m_percentage_text.text = String.format("M: %.2f", currentHHF!!.hhf!! * .85)
        g_percentage_text.text = String.format("G: %.2f", currentHHF!!.hhf!! * .95)
    }
}
