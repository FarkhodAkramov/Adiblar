package com.example.adiblarfirebase.models

import java.io.Serializable

class Writer : Serializable {
    var id: String? = null
    var name: String? = null
    var period: String? = null
    var type: String? = null
    var information: String? = null
    var photo: String? = null
    var saved: Boolean? = null




    constructor(
        id: String?,
        name: String?,
        period: String?,
        type: String?,
        information: String?,
        photo: String?,
        saved: Boolean?
    ) {
        this.id = id
        this.name = name
        this.period = period
        this.type = type
        this.information = information
        this.photo = photo
        this.saved = saved
    }
    constructor()
}