package com.jerry.common.webclient

open class HostUri(
    val domain: String,
    val path: String
)

object KakaoDeveloperOpenApiHostUri : HostUri(
    domain = "",
    path = ""
)

object NaverDeveloperOpenApiHostUri : HostUri(
    domain = "",
    path = ""
)
