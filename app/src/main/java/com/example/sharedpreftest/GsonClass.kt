package com.example.sharedpreftest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

fun main(){
    var gsonconv = GsonConvert()
    gsonconv.put(Contact("lkh","01"))
    gsonconv.put(Contact("cjs","02"))
    gsonconv.put(Contact("les","03"))
    gsonconv.put(Contact("les","03"))

    var contacts =gsonconv.get()
    contacts.forEach({
        println(it.toString())
    })
    var js = gsonconv.jsGet()
    println(js)

    var gs = gsonconv.gsGet()
    println(gs)

    gsonconv.put(Contact("les","03"))
    println(gsonconv.lastIndex())
}


class GsonConvert (var person:Contact = Contact("","")){
    //variable define
    var gson : Gson
    var listType : TypeToken<MutableList<Contact>> = object : TypeToken<MutableList<Contact>>() {}
    var js :String = ""
    var gs = mutableListOf<Contact>()
    companion object{
        private var lastindex = 0   //lastindex
        var contacts = mutableListOf<Contact>()
    }
    init {
        gson = GsonBuilder().create() //gson객체 만들기
    }
    //Contact 객체 추가
    fun put(contact : Contact) {
        contacts.add(contact)
        lastindex = contacts.lastIndex
    }
    //Contacts List 객체 돌려주기
    fun get():MutableList<Contact>{
        return contacts
    }
    //Json 객체 돌려주기
    fun jsGet():String{
        js  = gson.toJson(contacts,listType.type)
        return js
    }
    //Gson 객체 돌려주기
    fun gsGet():MutableList<Contact> {
        gs  = gson.fromJson(js,listType.type)
        return gs
    }
    //Contacts List lastIndex 값 돌려주기
    fun lastIndex():Int{
        lastindex = contacts.lastIndex
        return lastindex
    }

}

data class Contact(var name:String, var number:String)