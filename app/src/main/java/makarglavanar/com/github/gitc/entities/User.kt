package makarglavanar.com.github.gitc.entities

import java.io.Serializable

data class User(val login: String, val id: String, val avatar_url: String, val name: String, val company: String? = "company",
                val email: String? = "email", val location: String? = "location", val followers: String, val created_at: String) : Serializable{

//    fun getFixedCreated_at(): String {
//        var year = ""
//        var month = ""
//        var day = ""
//        for (i in 0..3) year += created_at.charAt(i)
//        for (i in 5..6) month += created_at.charAt(i)
//        for (i in 8..9) day += created_at.charAt(i)
//        return "$year-$month-$day"
//    }

}