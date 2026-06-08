package com.example.alpplay.domain.utils

import com.example.alpplay.domain.model.Channel

object M3uParser {
    fun parse(rawM3uData: String): List<Channel> {
        val channels = mutableListOf<Channel>()
        val lines = rawM3uData.lines()

        var currentName = ""
        var currentLogo = ""
        var currentCategory = "Other"

        for (line in lines) {
            val trimmedLine = line.trim()

            if (trimmedLine.startsWith("#EXTINF:")) {
                val logoMatch = "tvg-logo=\"([^\"]+)\"".toRegex().find(trimmedLine)
                currentLogo = logoMatch?.groups?.get(1)?.value ?: ""


                val groupMatch = "group-title=\"([^\"]+)\"".toRegex().find(trimmedLine)
                currentCategory = groupMatch?.groups?.get(1)?.value ?: "Other"


                currentName = trimmedLine.substringAfterLast(",").trim()

            } else if (trimmedLine.isNotEmpty() && !trimmedLine.startsWith("#")) {

                channels.add(
                    Channel(
                        name = currentName,
                        logoUrl = currentLogo,
                        category = currentCategory,
                        streamUrl = trimmedLine
                    )
                )
            }
        }
        return channels
    }
}