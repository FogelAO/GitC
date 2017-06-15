package makarglavanar.com.github.gitc.entities

data class IssueComment(val url: String,
                        val id: String,
                        val user: User,
                        val created_at: String,
                        val body: String) {
    val date: String
        get() {
            val stringBuilder = StringBuilder()
            for (i in 29..url.length - 1) {
                stringBuilder.append(url[i])
            }
            return stringBuilder.toString()
        }
}