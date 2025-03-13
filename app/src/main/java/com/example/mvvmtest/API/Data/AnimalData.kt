package com.example.mvvmtest.API.Data

class AnimalData {
    var result = ResultData()

    class ResultData {
        var limit = 0 // 0:Images , 1:Files
        var offset: Int? = null
        var count: Int? = null
        var sort: String? = null
        var results = ArrayList<ResultsData>()
    }

    class ResultsData {
        var _id: String?= null
        var _importdate = ImportdateData()
        var a_name_ch: String?= null
        var a_summary: String?= null
        var a_keywords: String?= null
        var a_alsoknown: String?= null
        var a_geo: String?= null
        var a_location: String?= null
        var a_poigroup: String?= null
        var a_name_en: String?= null
        var a_name_latin: String?= null
        var a_phylum: String?= null
        var a_class: String?= null
        var a_order: String?= null
        var a_family: String?= null
        var a_conservation: String?= null
        var a_distribution: String?= null
        var a_habitat: String?= null
        var a_feature: String?= null
        var a_behavior: String?= null
        var a_diet: String?= null
        var a_interpretation: String?= null
        var a_theme_name: String?= null
        var a_theme_url: String?= null
        var a_adopt: String?= null
        var a_code: String?= null
        var a_pic01_alt: String?= null
        var a_pic01_url: String?= null
        var a_pic02_alt: String?= null
        var a_pic02_url: String?= null
        var a_pic03_alt: String?= null
        var a_pic03_url: String?= null
        var a_pic04_alt: String?= null
        var a_pic04_url: String?= null
        var a_pdf01_alt: String?= null
        var a_pdf01_url: String?= null
        var a_pdf02_alt: String?= null
        var a_pdf02_url: String?= null
        var a_voice01_alt: String?= null
        var a_voice01_url: String?= null
        var a_voice02_alt: String?= null
        var a_voice02_url: String?= null
        var a_voice03_alt: String?= null
        var a_voice03_url: String?= null
        var a_vedio_url: String?= null
        var a_update: String?= null
        var a_cid: String?= null
    }

    class ImportdateData {
        var date: String?= null
        var timezone_type: String?= null
        var timezone: String?= null
    }
}