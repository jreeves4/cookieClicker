package com.example.cookieclicker

class Game(cookies: Int, multiplier: Int) {
    private var cookies = cookies
    private var multiplier = multiplier
    fun getCookies() : Int {
        return cookies
    }

    fun clickCookie() {
        this.cookies += multiplier
    }
}