package com.example.finalapplication

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class IntroResponse(
    @Element
    val body: myIntroBody
)

@Xml(name="body")
data class myIntroBody (
    @Element
    val items: myIntroItems
)

@Xml(name="items")
data class myIntroItems (
    @Element
    val item: MutableList<myIntroItem>
)

@Xml(name="item")
data class myIntroItem (
    @PropertyElement
    val contentid: Int?,
    @PropertyElement
    val tel: String?,
    @PropertyElement
    val overview: String?
) {
    constructor(): this(null, null, null)
}

