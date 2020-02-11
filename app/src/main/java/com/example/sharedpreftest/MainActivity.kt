package com.example.sharedpreftest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    //var pref = this.getPreferences(Context.MODE_PRIVATE) //Context.MODE_PRIVATE 0과 같음
    //var editor = pref.edit()
    var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //var gsonconv = GsonConvert()

        btSave.setOnClickListener({
            var name = etLoader.text.toString()
            var number = App.prefs.lastIndex().toString()
            var contact =Contact(name,number)
            App.prefs.put("key",contact)
            //var js = App.prefs.jsGet()
            //Log.d(TAG,js.toString())

            //App.prefs.setV("key",js)
            var jgs = App.prefs.getV("key")
            Log.d(TAG,jgs.toString())
            txtLoader.text = jgs.toString()

        })
        btSerch.setOnClickListener({
            try {
                var contactsF = App.prefs.get("key")
                var findContact = Contact("","")
                var keyin = etLoader.text.toString()
                if(keyin != "") {
                    contactsF.forEach({
                        if (it.key == keyin) {
                            findContact = it.value
                        }
                        txtLoader.text = findContact.toString()
                    })
                } else {
                    txtLoader.text = contactsF.toString()
                }

                Log.d(TAG,contactsF.toString())
                Log.d(TAG,findContact.toString())

            }catch (e:Exception){
                e.printStackTrace()
            }

        })
        btReset.setOnClickListener({
            App.prefs.clear("key")

        })

    }
}
