package com.gelugu.scheduler.domain

data class Task(
    val name: String,
    val date: Long,
    val status: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}