package com.dot.codot.collector.exchange.upbit

import com.dot.codot.collector.exchange.infra.BaseAPIProcessor
import com.dot.codot.controller.baseCoin.BaseCoinView
import com.dot.codot.service.BaseCoinService
import com.dot.codot.service.CoinTickerService
import io.reactivex.schedulers.Schedulers
import org.springframework.beans.factory.annotation.Autowired

class UpbitProcessor : BaseAPIProcessor {
  lateinit var apiService: UpbitAPIService

  @Autowired
  lateinit var baseCoinService: BaseCoinService
  @Autowired
  lateinit var coinTickerService: CoinTickerService

  constructor(apiService: UpbitAPIService) : super() {
    this.apiService = apiService
  }

  override fun marketProcess() {
    apiService.getMarkets().subscribe({
      it.forEach { it ->
        val currency = it.market.split("-")
        if (currency[0].equals("KRW")) {
          baseCoinService.createBaseCoin(BaseCoinView(exchange = "UPBIT", coin = currency[1], currency = currency[0]))
        }
      }
    }, { t -> t.printStackTrace() })
  }

  override fun tickerProcess(markets: String) {
    apiService.getTickers(markets).subscribe({
      it.forEach { coinTickerService.updateCoinTicker(it) }
      println("${it.size} tickers Update!!")
    }, { t -> t.printStackTrace() })
  }

}