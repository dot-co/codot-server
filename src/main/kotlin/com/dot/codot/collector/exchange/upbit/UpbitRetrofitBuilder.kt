package com.dot.codot.collector.exchange.upbit

import com.dot.codot.collector.exchange.infra.BaseAPIService
import com.dot.codot.collector.exchange.infra.BaseRetrofitBuilder

/**
 * Api docs -> https://docs.upbit.com/docs/upbit-quotation-restful-api
 */
class UpbitRetrofitBuilder : BaseRetrofitBuilder() {
  override var baseUrl: String = "https://api.upbit.com/v1/"
  override var apiService: Class<out BaseAPIService> = UpbitAPIService::class.java

}