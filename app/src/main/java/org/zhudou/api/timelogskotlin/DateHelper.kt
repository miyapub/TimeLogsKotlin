package org.zhudou.api.timelogskotlin
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by morgan on 2017/8/20.
 */
class DateHelper(){
    fun now(): String {
        val simpleFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val curDate = Date(System.currentTimeMillis())//获取当前时间
        var timeString = simpleFormat.format(curDate)//单位秒
        return timeString
    }
    fun okTime(begin_date:String,end_date:String): String {
        var simpleFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val fromDate = simpleFormat.format(simpleFormat.parse(begin_date))
        val toDate = simpleFormat.format(simpleFormat.parse(end_date))
        val from = simpleFormat.parse(fromDate).time
        val to = simpleFormat.parse(toDate).time
        val days = ((to - from) / (1000 * 60 * 60 * 24))
        var hours = ((to - from) / (1000 * 60 * 60))
        var minutes = ((to - from) / (1000 * 60))
        var second = ((to - from) / (1000))

        while(hours>24){
            hours -= 24;
        }

        while(minutes>60){
            minutes -= 60;
        }

        while(second>60){
            second -= 60;
        }

        var oktime="$days 日 $hours 时 $minutes 分 $second 秒"

        if(days<=0){
            oktime="$hours 时 $minutes 分 $second 秒"
            if(hours<=0){
                oktime="$minutes 分 $second 秒"
                if(minutes<=0){
                    oktime="$second 秒"
                }
            }
        }
        return oktime
    }

}