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
        return stringBuilder.toString()
    }

    fun isRoot(): Boolean {
        val urlWithoutBranch = url.split("?")[0]
        val clearPath = urlWithoutBranch.replaceAfter("contents/", "")
        val filePath = clearPath + name

        println(urlWithoutBranch == filePath)
        return true
    }

    fun getRootPath(): String {
        val urlWithoutBranch = url.split("?")[0]
        val countOfSLashes = path.count { c -> c == '/' }
        val root = path.split("/")[countOfSLashes - 2]

        val rootPath = urlWithoutBranch.replaceAfter("$root/", "")
        return rootPath
    }
}