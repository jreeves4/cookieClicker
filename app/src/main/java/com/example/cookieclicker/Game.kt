package com.example.cookieclicker

class Game(cookies: Int, multiplier: Int) {
    private var cookies = cookies
    private var multiplier = multiplier
    private lateinit var upgrades : Map<UpgradeType, Int>
    fun getCookies() : Int {
        return cookies
    }

    fun clickCookie() {
        this.cookies += multiplier
    }

    fun getUpgrades() : Map<UpgradeType, Int> {
        return upgrades
    }

    fun setUpgrades(upgrades : Map<UpgradeType, Int>) {
        this.upgrades = upgrades
    }
}