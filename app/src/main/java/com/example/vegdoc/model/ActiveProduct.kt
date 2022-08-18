package com.example.vegdoc.model

data class ActiveProduct(
    var activeIngredient: Product,
    var tradeNames: MutableList<Product>
)