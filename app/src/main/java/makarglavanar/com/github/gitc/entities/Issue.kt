package makarglavanar.com.github.gitc.entities

import java.io.Serializable

data class Issue(val url: String,
                 val id: String,
                 val title: String,
                 val user: User,
                 val state: String,
                 val body: String,
                 val created_at: String,
                 val comments: String?) : Serializable {

    val date: String
        get() {
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

    fun getFormattedComments(): String? {
        if (comments == null) return null

        val stringBuilder = StringBuilder()
        for (i in 29..url.length - 1) {
            stringBuilder.append(url[i])
        }
        return stringBuilder.toString() + "/comments"
    }
}