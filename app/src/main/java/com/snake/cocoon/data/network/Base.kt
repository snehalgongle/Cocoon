package com.snake.cocoon.data.network

data class Base (

    val status : String,
    val copyright : String,
    val section : String,
    val last_updated : String,
    val num_results : Int,
    val results : List<Results>
)