package com.aliayali.news.model

enum class NewsCategory(
    val key: String,
    val query: String,
) {
    ALL(
        key = "all",
        query = "طلا OR دلار OR بیت کوین OR بورس OR اقتصاد OR نفت",
    ),
    CURRENCY(
        key = "currency",
        query = "دلار",
    ),
    GOLD(
        key = "gold",
        query = "طلا",
    ),
    CRYPTO(
        key = "crypto",
        query = "بیت کوین",
    ),
    STOCK(
        key = "stock",
        query = "بورس",
    ),
    ECONOMY(
        key = "economy",
        query = "اقتصاد",
    ),
    OIL(
        key = "oil",
        query = "نفت",
    ),
}