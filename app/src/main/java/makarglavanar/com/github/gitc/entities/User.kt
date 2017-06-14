package makarglavanar.com.github.gitc.entities

import java.io.Serializable

data class User(val login: String,
                val id: String,
                val avatar_url: String,
                val name: String,
                val company: String?,
                val email: String?,
                val location: String?,
                val followers: String,
                val created_at: String) : Serializable {


    fun getFormattedDate(): String {
        val tempData = StringBuilder()
        for (i in 0..9)
            tempData.append(created_at[i])
        return tempData.toString()
    }
}