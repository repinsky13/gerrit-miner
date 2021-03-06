package org.jetbrains.research.gerritminer.main

import org.jetbrains.research.gerritminer.client.Client
import org.jetbrains.research.gerritminer.model.flushToFile
import org.jetbrains.research.gerritminer.model.outputPath
import java.io.File
import java.lang.Integer.parseInt
import java.time.Instant
import java.time.format.DateTimeFormatter


fun main(args: Array<String>) {
    if (args.size == 4) {
        val baseUrl = args[0]
        val repoName = args[1]
        val project = args[2]
        val limit: Int
        try {
            limit = parseInt(args[3])
        } catch (e: NumberFormatException) {
            println("Last argument must be a number! \n")
            printUsageInfo()
            return
        }
        val client = Client()
        val rawData = client.getResponseText(client.constructQuery(baseUrl, project,  "merged", limit))
        println(rawData)
        val reviewsData = client.loadGerritReviews(baseUrl, project,  "merged", limit)
        reviewsData.sortedBy { it.timestamp }
        val projectPath: String = System.getProperty("user.dir")
        val timeStamp: String = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        File(projectPath).resolve("${outputPath}raw-$repoName-$timeStamp.json").writeText(rawData)
        println("Total Entries: ${reviewsData.size}")
        flushToFile(reviewsData, repoName)
    } else {
        println("Incorrect number of arguments! \n")
        printUsageInfo()
    }
}

fun printUsageInfo() {
    println("Usage: ./gradlew run --args='arg1 arg2 arg3 arg4' \nWhere arg1 - baseUrl (for instance: https://review.opendev.org), arg2 - project codename for saving results (i.e. opendev), arg3 is a specific project for which reviews should be mined and arg4 is the number of review to mine")
}

