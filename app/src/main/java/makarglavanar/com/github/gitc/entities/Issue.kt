package makarglavanar.com.github.gitc.entities

data class Issue(val url: String,
                 val id: String,
                 val title: String,
                 val user: User,
                 val state: String,
                 val created_at: String,
                 val comments: String?) {
    fun getFormattedDate(): String {
        val tempData = StringBuilder()
        for (i in 0..9)
            tempData.append(created_at[i])
        return tempData.toString()
    }
}