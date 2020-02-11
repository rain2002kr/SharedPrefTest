package com.example.sharedpreftest

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.Exception

fun main (){

}

class MySharedPreferences(context: Context) {
    val TAG ="MySharedPreferences"
    val PREFS_FILENAME = "prefs"
    val PREF_KEY_MY_EDITTEXT = "myEditText"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    /* 파일 이름과 EditText를 저장할 Key 값을 만들고 prefs 인스턴스 초기화 */
    //key값을 이용한 저장
    var gson : Gson
    var listType : TypeToken<MutableMap<String,Contact>> = object : TypeToken<MutableMap<String,Contact>>() {}
    var js :String = ""
    var gs = mutableMapOf<String,Contact>()
    init {
        gson = GsonBuilder().create() //gson객체 만들기
        lastindex = 0
    }
    companion object {
        private var lastindex = 0   //lastindex
        var contacts = mutableMapOf<String,Contact>()
        var contactList = mutableListOf<Contact>()
    }
    //Contact 객체 추가

    fun put(findkey:String, contact : Contact) {
        try {
            var js = prefs.getString(findkey,"")
            gs = gson.fromJson(js, listType.type)
            if (contacts != gs) {
                contacts = gs
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        contactList.add(contact)
        lastIndex(contactList)
        contacts.put(contactList.lastIndex.toString(),contact)
        var js = gson.toJson(contacts, listType.type)
        prefs.edit().putString(findkey,js).apply()
    }

    //Contacts List 객체 돌려주기
    fun get():MutableMap<String,Contact>{
        return contacts
    }
    //Contacts List 객체 돌려주기
    fun get(findkey: String):MutableMap<String,Contact>{
    //추가하기전 pref에 저장되어있는 js 가져와서 contact객체로 만들고 비교후에 없으면 추가 후 new값 추가
        var savedJS = prefs.getString(findkey,"")
        var savedGs = gsGet(savedJS)

        return contacts
    }
    //Json 객체 돌려주기
    fun jsGet():String{
        js  = gson.toJson(contacts,listType.type)
        return js
    }
    //Gson 객체 돌려주기
    fun gsGet():MutableMap<String,Contact> {
        gs  = gson.fromJson(js,listType.type)
        return gs
    }
    //입력된 jsson을  Gson 객체 돌려주기
    fun gsGet(json:String?):MutableMap<String,Contact> {
        gs  = gson.fromJson(json,listType.type)
        return gs
    }
    //Contacts List lastIndex 값 돌려주기
    fun lastIndex():Int{
        lastindex = lastIndex(contactList)
        return lastindex
    }
    fun lastIndex(contacts:MutableList<Contact>):Int{
        lastindex = contacts.lastIndex
            lastindex += 1
        return lastindex
    }
    fun setV(key:String, value:String? ){
        prefs.edit().putString(key,value).apply()
     }
    //key값을 이용한 로드
    fun getV(key:String):String?{
        return prefs.getString(key,"")
    }

    fun clear(key:String){
        contactList.clear()
        contacts.clear()
        prefs.edit().clear().commit()
        prefs.edit().remove("key").commit()

    }
    var myEditText: String?
        get() = prefs.getString(PREF_KEY_MY_EDITTEXT, "")
        set(value) = prefs.edit().putString(PREF_KEY_MY_EDITTEXT, value).apply()
    /* get/set 함수 임의 설정. get 실행 시 저장된 값을 반환하며 default 값은 ""
     * set(value) 실행 시 value로 값을 대체한 후 저장 */
}

class App : Application() {

    companion object {
        lateinit var prefs : MySharedPreferences
    }
    /* prefs라는 이름의 MySharedPreferences 하나만 생성할 수 있도록 설정. */

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()
    }
}