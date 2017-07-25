package makarglavanar.com.github.gitc.entities


data class File(val name: String,
                val type: String,
                val path: String,
                val size: String,
                val content: String,
                val url: String) {

    fun isFile() = type == "file"

    fun isDir() = type == "dir"

    fun getFormattedFileUrl(): String {
        val stringBuilder = StringBuilder()
        for (i in 29..url.length - 1) {
            stringBuilder.append(url[i])
        }
        val tmpString = stringBuilder.toString().split("?")[0]
        return tmpString
    }

    fun isRoot() = path == name

    fun getRootPath(): String {
        val countOfSLashes = path.count { c -> c == '/' }
        val root = path.replaceAfter(path.split("/")[countOfSLashes - 1], "")
        return root
    }

    fun getFormattedDirUrl(): String {
        val stringBuilder = StringBuilder()
        for (i in 29..url.length - 1) {
            stringBuilder.append(url[i])
        }
//        var tmpString = stringBuilder.toString().replaceAfter("?", "looool")
////        tmpString = tmpString.split("?")[0]
////        tmpString = tmpString.replaceAfter(name + "/", "")
//        val stringBuilder2 = StringBuilder()
//        for (i in 0..tmpString.length - 8) {
//            stringBuilder2.append(tmpString[i])
//        }
//        var tmpString2 = tmpString.split("contents")[0] + tmpString.split("contents")[1]
        return stringBuilder.toString().split("?")[0]
    }
}