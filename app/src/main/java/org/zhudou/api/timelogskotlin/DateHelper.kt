package org.zhudou.api.timelogskotlin

import java.text.SimpleDateFormat



/**
 * Created by morgan on 2017/8/20.
 */
class DateHelper(){
    fun okTime(begin_date:String,end_date:String): String {
        var simpleFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val fromDate = simpleFormat.format(simpleFormat.parse(begin_date))
        val toDate = simpleFormat.format(simpleFormat.parse(end_date))
        val from = simpleFormat.parse(fromDate).time
        val to = simpleFormat.parse(toDate).time
        val days = ((to - from) / (1000 * 60 * 60 * 24)).toInt()
        val hours = ((to - from) / (1000 * 60 * 60))
        val minutes = ((to - from) / (1000 * 60))
        val second = ((to - from) / (1000))
        return "$days 天 $hours 时 $minutes 分 $second 秒"
    }

}