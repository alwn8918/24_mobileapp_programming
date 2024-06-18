package com.example.finalapplication

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class ListResponse(
    @Element
    val body: myXmlBody
)

@Xml(name="body")
data class myXmlBody(
    @Element
    val items: myXmlItems
)

@Xml(name="items")
data class myXmlItems(
    @Element
    val item: MutableList<myXmlItem>
)

@Xml(name="item")
data class myXmlItem(
    @PropertyElement
    val title: String?,
    @PropertyElement
    val addr1: String?,
    @PropertyElement
    val cat1: String?,
    @PropertyElement
    val cat2: String?,
    @PropertyElement
    val cat3: String?,
    @PropertyElement
    val firstImage: String?
) {
    constructor(): this(null, null, null, null, null, null)
}
