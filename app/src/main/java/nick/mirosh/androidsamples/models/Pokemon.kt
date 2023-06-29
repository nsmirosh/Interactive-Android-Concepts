package nick.mirosh.androidsamples.models

data class Pokemon(
    val name: String,
    val url: String,
    var isExpanded: Boolean = false,
    var color: Int = 0
    )
