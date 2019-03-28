package com.example.imageonline.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Url {

    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("template")
    @Expose
    var template: String? = null

}