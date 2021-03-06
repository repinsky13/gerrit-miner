package org.jetbrains.research.gerritminer.model

import java.sql.Timestamp


data class Review(
    val legacyId: Int,
    val id: String,
    val files: List<String>,
    val commitInfo: CommitInfo,
    val reviewers: List<UserInfo>,
    val author: UserInfo,
    val timestamp: Timestamp
)

data class UserInfo(val userID: Int, val username: String, val email: String, val displayName: String)

data class CommitInfo(
    val commitId: String,
    val project: String,
    val branch: String
)