package tekproject.dev_epicture.epicture.Model

data class HomeImagesModel(
    val imageId: String,
    val accountId: String,
    val imageTile: String,
    val imageUrl: String,
    var avatarUrl: String,
    var username: String,
    var isFavorite: String
    )