package com.dot.codot.collector.exchange.upbit

import com.dot.codot.collector.exchange.infra.BaseAPIService
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface UpbitAPIService : BaseAPIService {
  @GET("market/all")
  fun getMarkets(): Observable<List<UpbitMarket>>


  @GET("ticker")
  fun getTickers(@Query("markets") markets: String): Observable<List<UpbitTicker>>
}