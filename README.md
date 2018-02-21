# SocialLinkify
An Android library for linking @ mentions &amp; Hashtags to their corresponding social network

# Motivation

For most of our projects we handle multiple social network types, which results in us needing to
handle multiple link destinations for @ mentions and Hashtags. For example, if the @Buffer mention
is used then this library will allow you to make that a clickable span pointing to either the Buffer Twitter profile,
Facebook Page or Instagram profile. The same can be done for Hashtags. Rather than duplicate code across
our projects we decided to create a small helper library to separate this from our projects.

# How to use

There is only one function to call from SocialLinkify, that is [socialLinkifyText()](https://github.com/bufferapp/SocialLinkify/blob/task/open-source-project/lib/src/main/java/org/buffer/sociallinkify/SocialLinkify.kt#L32). This will return
you an instance of a Spannable which you can then use to set your textview content to. This function
takes four parameters:

- color - An Int representing the color you wish to use for linked elements

- text - The String that you wish to apply linking to

- socialNetwork - The [SocialNetwork](https://github.com/bufferapp/SocialLinkify/blob/task/open-source-project/lib/src/main/java/org/buffer/sociallinkify/model/SocialNetwork.kt) which you want the elements to be linked to. This can be either
TWITTER, FACEBOOK or INSTAGRAM.

- patternTypes - A varargs parameter for the [PatternType](https://github.com/bufferapp/SocialLinkify/blob/task/open-source-project/lib/src/main/java/org/buffer/sociallinkify/model/PatternType.kt) patterns that you want to be recognised, this can be
any collection of HASHTAG, MENTION and URL.

For example, if I want to create linked spans for Twitter @ mentions, Hashtags & URLs:

    text_message.text = socialLinkify.socialLinkifyText(
            ContextCompat.getColor(context, R.color.some_color), message.text,
            SocialNetwork.TWITTER, PatternType.HASHTAG, PatternType.MENTION, PatternType.URL)

or if I want to create linked spans for Instagram @ mentions only:

    text_message.text = socialLinkify.socialLinkifyText(
            ContextCompat.getColor(context, R.color.some_color), message.text,
            SocialNetwork.INSTAGRAM, PatternType.MENTION)

or if I just want to create linked spans for Facebook hashtags:

    text_message.text = socialLinkify.socialLinkifyText(
            ContextCompat.getColor(context, R.color.some_color), message.text,
            SocialNetwork.FACEBOOK, PatternType.HASHTAG)

The SocialNetwork class also has a handy [fromString()](https://github.com/bufferapp/SocialLinkify/blob/task/open-source-project/lib/src/main/java/org/buffer/sociallinkify/model/SocialNetwork.kt#L7) function to allow you to retrieve a SocialNetwork
instance from some string that may be used in your app such as "twitter".
