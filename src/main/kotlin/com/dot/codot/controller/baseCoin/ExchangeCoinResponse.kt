package com.dot.codot.controller.baseCoin

import java.io.Serializable
data class ExchangeCoinResponse(var data : HashMap<String, ArrayList<String>> = HashMap<String, ArrayList<String>>()) : Serializable