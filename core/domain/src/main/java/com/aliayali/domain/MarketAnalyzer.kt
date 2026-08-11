package com.aliayali.domain

import com.aliayali.model.analysis.MarketAnalysis
import com.aliayali.model.analysis.MarketSnapshot

interface MarketAnalyzer {
    fun analyze(snapshot: MarketSnapshot): MarketAnalysis
}