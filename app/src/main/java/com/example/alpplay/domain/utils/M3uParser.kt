package com.example.alpplay.domain.utils

import com.example.alpplay.domain.model.Channel
import java.io.InputStream

object M3uParser {

    fun parse(inputStream: InputStream): List<Channel> {
        val channels = mutableListOf<Channel>()

        var currentTitle: String = "Unknow"
        var currentCategory: String = "Other"
        var currentLogo: String = ""

        inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val trimmedLine = line.trim()

                if (trimmedLine.startsWith("#EXTINF")) {
                    currentTitle = extractTitle(trimmedLine)
                    currentCategory = extractCategory(trimmedLine)
                    currentLogo = extractLogo(trimmedLine)
                } else if (trimmedLine.isNotEmpty() && !trimmedLine.startsWith("#")) {
                    channels.add(
                        Channel(
                            name = currentTitle,
                            category = currentCategory,
                            logoUrl = currentLogo,
                            streamUrl = trimmedLine
                        )
                    )
                }
            }
        }
        return channels
    }

    private fun extractTitle(line: String): String {
        return line.substringAfterLast(",", "Unknow channel").trim()
    }

    private fun extractCategory(line: String): String {
        val category = Regex("group-title=\"(.*?)\"").find(line)?.groupValues?.get(1)
        return category ?: "Other"
    }

    private fun extractLogo(line: String): String {
        return Regex("tvg-logo=\"(.*?)\"").find(line)?.groupValues?.get(1) ?: ""
    }
}
