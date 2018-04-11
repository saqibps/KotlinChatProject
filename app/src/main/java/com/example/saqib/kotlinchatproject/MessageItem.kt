package com.example.saqib.kotlinchatproject

data class MessageItem(val messageText:String, val uid:String) {
    constructor() : this("","")
}