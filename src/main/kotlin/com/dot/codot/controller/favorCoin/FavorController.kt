package com.dot.codot.controller.favorCoin

import com.dot.codot.model.BaseCoin
import com.dot.codot.model.CoinTicker
import com.dot.codot.service.CoinTickerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/favor")
class FavorCntroller {

    @Autowired
    lateinit var coinTickerService : CoinTickerService

    @RequestMapping(value = ["/get/{exchange}/{coin}/{currency}"], method = [RequestMethod.GET])
    fun getFavorCoins(@PathVariable("exchange") exchange : String, @PathVariable("coin") coin : String, @PathVariable("currency") currency : String) : FavorCoinView? {
        var baseCoin = BaseCoin(exchange, coin, currency)
        return coinTickerService.getCoinTicker(baseCoin, currency)
    }


    @RequestMapping(value = ["/getList"], method = [RequestMethod.GET])
    fun getFavorCoins(@RequestParam("favor") favorMap : Map<String, List<String>>) : List<CoinTicker>{
//        val map = HashMap<String, List<String>>()
//        map.put("UPBIT", listOf("BTC", "ETH"))
        return coinTickerService.getFavors(favorMap)
    }
}