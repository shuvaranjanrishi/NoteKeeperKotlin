package com.therishideveloper.notekeeperkotlin.model

/**
 * Created by Shuva Ranjan Rishi on 17,March,2023
 * BABL, Bangladesh,
 */

data class NoteResponse(
    var userId: Int,
    var id: Int,
    var title: String,
    var body: String,
)
