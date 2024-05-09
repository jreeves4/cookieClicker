
package com.example.cookieclicker

class Game(cookies: Int, multiplier: Int) {
    private var cookies = cookies
    private var multiplier = multiplier
    private var upgrades : Map<UpgradeType, Int>

    init {
        val initialUpgrades = UpgradeType.entries.associateWith { 0 }
        upgrades = initialUpgrades
    }

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
    fun jackpot() {
        this.cookies += 99 * multiplier
    }

    fun getMultiplier() : Int {
        return multiplier
    }

    fun buyTwoX() {
        if (cookies >= 100) {
            this.cookies -= 100
            this.multiplier *= 2
        }
    }

    fun buyThreeX() {
        if (cookies >= 275) {
            this.cookies -= 275
            this.multiplier *= 3
        }
    }
}