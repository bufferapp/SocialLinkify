package org.buffer.sociallinkify.model

enum class SocialNetwork(val id: String) {
    TWITTER("twitter"),
    FACEBOOK("facebook"),
    INSTAGRAM("instagram");

    companion object {
        fun fromString(name: String) = when (name) {
            TWITTER.id -> TWITTER
            FACEBOOK.id -> FACEBOOK
            INSTAGRAM.id -> INSTAGRAM
            else -> throw IllegalArgumentException("Whoops, the social network $name isn't known!")
        }
    }
}
