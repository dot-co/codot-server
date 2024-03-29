package com.dot.codot.collector.exchange.cryptopia

import com.dot.codot.collector.exchange.infra.BaseAPIService
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface
CryptopiaAPIService : BaseAPIService {
    @GET("GetMarkets")
    fun getMarkets() : Observable<CryptopiaContext<CryptopiaMarket>>

    @GET("GetCurrencies")
    fun getCurrencies() : Observable<CryptopiaContext<CryptopiaCurrency>>
}
