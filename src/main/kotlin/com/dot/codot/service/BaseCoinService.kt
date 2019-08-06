package com.dot.codot.service

import com.dot.codot.controller.baseCoin.BaseCoinView
import com.dot.codot.controller.baseCoin.ExchangeCoinResponse
import com.dot.codot.model.BaseCoin
import com.dot.codot.model.BaseCoinRepository
import com.dot.codot.model.infra.BaseService
import com.dot.codot.infra.BaseException
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BaseCoinService : BaseService() {

    @Autowired
    lateinit var repository : BaseCoinRepository

    fun createBaseCoin(baseCoinView : BaseCoinView) {
        var baseCoin : BaseCoin = BaseCoin()
        BeanUtils.copyProperties(baseCoinView, baseCoin)
        repository.addBaseCoin(baseCoin)
    }

    fun getBaseCoinList() : ExchangeCoinResponse {
        var result = ExchangeCoinResponse()
        var baseCoinList : List<BaseCoin> = repository.findAll()
        baseCoinList.forEach { baseCoin ->
            if(result.data.containsKey(baseCoin.exchange)) {
                result.data.get(baseCoin.exchange)?.add(baseCoin.coin)
            } else {
                result.data.put(baseCoin.exchange, arrayListOf(baseCoin.coin))
            }
        }
        return result
    }

    fun getBaseCoins(exchange : String) : List<BaseCoin> {
        val baseCoins = repository.findCoins(exchange)
        return if(baseCoins.isNotEmpty()) baseCoins else throw BaseException()
    }

    fun getBaseExchanges(coin : String) : List<BaseCoin> {
        return repository.findExchanges(coin)
    }
}