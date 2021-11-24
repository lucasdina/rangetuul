package com.gunnr.tuul

import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.gunnr.tuul.fragments.CalculatorFragment
import com.gunnr.tuul.fragments.ClassifiersFragment
import com.gunnr.tuul.fragments.RulesFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import com.google.android.gms.ads.AdRequest
import com.gunnr.tuul.services.ui.LoadingBar
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.net.Uri
import com.linkedin.android.shaky.EmailShakeDelegate
import com.linkedin.android.shaky.Shaky


class MainActivity : AppCompatActivity() {
    var loadingBar: LoadingBar? = null
    var PREFS_FIRST_RUN_KEY: String = "firstrun"
    var PREFS_NAME: String = "com.gunnr.tuul"
    var PREFS_WARN_ADOBE: String = "warnAdobe"
    var ADOBE_PACKAGE_NAME: String = "com.adobe.reader"

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        loadingBar = LoadingBar(this)
        loadingBar!!.showDialog()
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        when (item.itemId) {
            R.id.navigation_calculator -> {
                adView.visibility = View.VISIBLE
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, CalculatorFragment.newInstance(), "calculator")
                    .commit()
                loadingBar!!.hideDialog()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_classifiers -> {
                adView.visibility = View.GONE
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, ClassifiersFragment.newInstance(), "classifiers")
                    .commit()
                loadingBar!!.hideDialog()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_rules -> {
                adView.visibility = View.GONE
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, RulesFragment.newInstance(), "rules")
                    .commit()
                loadingBar!!.hideDialog()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Shaky.with(this.application, EmailShakeDelegate("lukeadina@gmail.com"))
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, CalculatorFragment.newInstance(), "calculator")
            .commit()

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        firstLaunchCheck(prefs)
    }

    private fun firstLaunchCheck(prefs: SharedPreferences){
        if(prefs.getBoolean(PREFS_FIRST_RUN_KEY, true)){
            prefs.edit().putBoolean(PREFS_FIRST_RUN_KEY, false).apply()
            val builder = AlertDialog.Builder(this@MainActivity)

            builder.setTitle("Greetings!")
            builder.setMessage("This app is not able to open any documents through Google products, we suggest using Adobe Reader if you plan on printing or searching through documents.")
            builder.setPositiveButton("OK"){ _, _ ->
                adobeCheck(prefs)
            }
            builder.setNeutralButton("INSTALL ADOBE READER"){ _, _ ->
                takeUserToPlayStore()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else {
            adobeCheck(prefs)
        }

    }

    private fun adobeCheck(prefs: SharedPreferences){
        if(prefs.getBoolean(PREFS_WARN_ADOBE, true)) {
            val userHasAdobe = isAppInstalled(ADOBE_PACKAGE_NAME)
            println(userHasAdobe)
            if (!userHasAdobe) {
                val builder = AlertDialog.Builder(this@MainActivity)

                builder.setTitle("Warning")
                builder.setMessage("It looks like you do not have Adobe Reader installed. Would you like to install it now?")
                builder.setPositiveButton("YES") { dialog, which ->
                    takeUserToPlayStore()
                }
                builder.setNegativeButton("NO") { dialog, which ->
                    // do nothing
                }
                builder.setNeutralButton("DON'T WARN ME AGAIN") { _, _ ->
                    prefs.edit().putBoolean(PREFS_WARN_ADOBE, false).apply()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    private fun takeUserToPlayStore(){
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$ADOBE_PACKAGE_NAME")))
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$ADOBE_PACKAGE_NAME")
                )
            )
        }

    }

    private fun isAppInstalled(packageName: String): Boolean {
        val pm = packageManager
        return try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
