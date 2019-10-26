package tekproject.dev_epicture.epicture.ApiRequests

private const val homeImageUrl      =   "https://api.imgur.com/3/gallery/{{section}}/{{sort}}/{{window}}/{{page}}?showViral={{showViral}}&mature={{showMature}}&album_previews={{albumPreviews}}"
private const val searchImage       =   "https://api.imgur.com/3/gallery/search/{{sort}}/{{window}}/{{page}}?q={{search}}"
private const val accountPostImage  =   "https://api.imgur.com/3/account/{{username}}/images"
private const val accountData       =   "https://api.imgur.com/3/account/{{username}}"
private const val favoriteId        =   "https://api.imgur.com/3/account/{{username}}/favorites/{{page}}/{{favoritesSort}}"
private const val favoriteUrl       =   "https://api.imgur.com/3/image/{{imageid}}"

private const val POSTFavAnImg      =   "https://api.imgur.com/3/image/{{imageid}}/favorite"
private const val POSTImg           =   "https://api.imgur.com/3/upload"

class UrlRequests {
    fun getHomeImages(section: String = "hot", sort: String = "viral", window: String = "day", page: String = "0"): String {
        return homeImageUrl
            .replace("{{section}}", section)
            .replace("{{sort}}", sort)
            .replace("{{window}}", window)
            .replace("{{page}}", page)
    }

    fun getSearchImages(sort: String = "time", window: String = "all", page: String = "0", search: String): String {
        return searchImage
            .replace("{{sort}}", sort)
            .replace("{{window}}", window)
            .replace("{{page}}", page)
            .replace("{{search}}", search)
    }

    fun getAccountPostImage(username: String): String {
        return accountPostImage
            .replace("{{username}}", username)
    }

    fun getAccountData(username: String): String {
        return accountData
            .replace("{{username}}", username)
    }

    fun getFavoriteImgId(username: String, page: String  = "0", sort: String = "0"): String {
        return favoriteId
            .replace("{{username}}", username)
            .replace("{{page}}", page)
            .replace("{{favoritesSort}}", sort)
    }

    fun getFavorite(imageId: String): String {
        return favoriteUrl
            .replace("{{imageid}}", imageId)
    }

    fun postFavorite(imageId: String) :String {
        return POSTFavAnImg
            .replace("{{imageid}}", imageId)
    }
}
