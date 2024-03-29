package com.dot.codot.service

import com.dot.codot.collector.exchange.cryptopia.Market
import com.dot.codot.collector.exchange.cryptopia.UpbitTicker
import com.dot.codot.collector.exchange.upbit.UpbitTicker
import com.dot.codot.controller.favorCoin.FavorCoinView
import com.dot.codot.model.BaseCoin
import com.dot.codot.model.BaseCoinRepository
import com.dot.codot.model.CoinTicker
import com.dot.codot.model.CoinTickerRepository
import com.dot.codot.model.infra.BaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CoinTickerService : BaseService() {

    @Autowired
    lateinit var coinTickerRepository : CoinTickerRepository

    @Autowired
    lateinit var baseCoinRepository : BaseCoinRepository

    fun getBaseCoinList() : List<FavorCoinView> {
        var baseCoinList : List<CoinTicker> = coinTickerRepository.findAll()
        return baseCoinList.map { it -> FavorCoinView(it) }
    }

    fun getCoinTicker(baseCoin: BaseCoin, currency : String) : FavorCoinView? {
        var coinTicker = coinTickerRepository.findCoinTicker(CoinTicker(coin = baseCoin))
        return if(coinTicker != null) FavorCoinView(coinTicker) else null
    }

    fun insertCoinTicker(market : Market) {
        val splited = market.label.split("/")
        val coin = splited[0]
        val currency = splited[1]
        val baseCoin = baseCoinRepository.findBaseCoin(BaseCoin("CRYPTOPIA", coin, currency))
        var coinTicker = CoinTicker(baseCoin!!, lastPrice = market.lastPrice!!)
        coinTickerRepository.add(coinTicker)
    }

    fun updateCoinTicker(ticker : UpbitTicker) {
        val splitTicker = ticker.market.split("-")
        val coin = splitTicker[1]
        val currency = splitTicker[0]
        val baseCoin = baseCoinRepository.findBaseCoin(BaseCoin("UPBIT", coin, currency))
        var coinTicker : CoinTicker = CoinTicker(
                coin = baseCoin!!,
                lastPrice = ticker.tradePrice,
                prevPrice = ticker.prevClosingPrice,
                highPrice = ticker.highPrice,
                lowPrice = ticker.lowPrice,
                volume = ticker.tradeVolume24h )

        var origin = coinTickerRepository.findCoinTicker(coinTicker)
        when(origin) {
            null -> coinTickerRepository.add(coinTicker)
            else -> {
                origin.volume = coinTicker.volume
                origin.prevPrice = coinTicker.prevPrice
                origin.lowPrice = coinTicker.lowPrice
                origin.highPrice = coinTicker.highPrice
                origin.lastPrice = coinTicker.lastPrice
                coinTickerRepository.update(origin)
            }
        }
    }


    fun getFavors(favorMap : Map<String, List<String>>) : List<CoinTicker> {
        val favors : ArrayList<CoinTicker> = arrayListOf()
        favorMap.forEach { it ->
            val coinTickerList:  List<CoinTicker> = coinTickerRepository.findByExchangeCoins(it.key, it.value)
            favors.addAll(coinTickerList)
        }
        return favors
    }
}