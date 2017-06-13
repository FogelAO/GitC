package makarglavanar.com.github.gitc.entities

import java.io.Serializable


data class Repository(val id: String,
                      val name: String,
                      val avatar_url: String,
                      val url: String,
                      val owner: User,
                      val created_at: String,
                      val language: String,
                      val watchers: String,
                      val open_issues: String,
                      val watchers_count: String) : Serializable