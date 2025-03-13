package com.example.mvvmtest.API.Data

class ParkData {
    var result = ResultData()

    class ResultData {
        var limit = 0 // 0:Images , 1:Files
        var offset: String? = null
        var count: String? = null
        var sort: String? = null
        var results = ArrayList<ResultsData>()
    }

    class ResultsData {
        var _id: String?= null
        var _importdate = ImportdateData()
        var e_no: String?= null
        var e_category: String?= null
        var e_name: String?= null
        var e_pic_url: String?= null
        var e_info: String?= null
        var e_memo: String?= null
        var e_geo: String?= null
        var e_url: String?= null
    }

    class ImportdateData {
        var date: String?= null
        var timezone_type: String?= null
        var timezone: String?= null
    }
}