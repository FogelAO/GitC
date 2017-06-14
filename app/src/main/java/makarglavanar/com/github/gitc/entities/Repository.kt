package makarglavanar.com.github.gitc.entities

import java.io.Serializable


data class Repository(val id: String,
                      val name: String,
                      val avatar_url: String,
                      val owner: User,
                      val created_at: String,
                      val language: String?,
                      val watchers: String,
                      val open_issues: String,
                      val description: String?,
                      val size: String,
                      val stargazers_count: String,
                      val watchers_count: String) : Serializable {

    val url: String
        get() {
            val stringBuilder = StringBuilder()
            for (i in 28..url.length) {
                stringBuilder.append(url[i])
            }
            return stringBuilder.toString()
        }

    fun getFormattedDate(): String {
        val tempData = StringBuilder()
        for (i in 0..9)
            tempData.append(created_at[i])
        return tempData.toString()
    }

}