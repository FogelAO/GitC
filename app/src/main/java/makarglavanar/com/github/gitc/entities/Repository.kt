package makarglavanar.com.github.gitc.entities

import java.io.Serializable


data class Repository(val id: String,
                      val name: String,
                      val owner: User,
                      val url: String,
                      val created_at: String,
                      val updated_at: String,
                      val language: String?,
                      val watchers: String,
                      val open_issues: String,
                      val description: String?,
                      val size: String,
                      val stargazers_count: String,
                      val watchers_count: String) : Serializable {

    val date: String
        get() {
            val stringBuilder = StringBuilder()
            for (i in 29..url.length - 1) {
                stringBuilder.append(url[i])
            }
            return stringBuilder.toString()
        }

    fun getFormattedUpdated(): String {
        val stringBuilder = StringBuilder()
        for (i in 29..created_at.length - 1) {
            stringBuilder.append(created_at[i])
        }
        return stringBuilder.toString()
    }

    fun getFormattedUrl(): String {
        val stringBuilder = StringBuilder()
        for (i in 29..url.length - 1) {
            stringBuilder.append(url[i])
        }
        return stringBuilder.toString()
    }
}