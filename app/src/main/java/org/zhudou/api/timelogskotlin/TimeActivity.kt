package org.zhudou.api.timelogskotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.UiThread
import android.view.View
import kotlinx.android.synthetic.main.activity_time.*
import android.R.attr.bitmap



class TimeActivity : AppCompatActivity() {

    var str_begin_time=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)
        var dbHelper=DbHelper(this)
        var c=dbHelper.cursor("select * from logs where status='doing' order by _id limit 0,1")
        if (c != null) {
            if (c.moveToNext()) {
                val str_id = c.getInt(c.getColumnIndex("_id"))
                val str_title = c.getString(c.getColumnIndex("title"))
                val str_status = c.getString(c.getColumnIndex("status"))
                str_begin_time = c.getString(c.getColumnIndex("begin_time"))
                var str_end_time = c.getString(c.getColumnIndex("end_time"))
                doing.setText(str_title)
                uptime()

                finish.setOnClickListener(View.OnClickListener {
                    dbHelper.exec("update logs set end_time=datetime('now','localtime'),status='finish' where _id='$str_id'")
                    val intent = Intent(this@TimeActivity, MainActivity::class.java)
                    startActivity(intent)
                })

                    object : Thread() {
                        override fun run() {
                            //这儿是耗时操作，完成之后更新UI；
                            while(true){
                                Thread.sleep(1000)
                                runOnUiThread {
                                    //更新UI
                                    uptime()
                                }
                            }
                        }
                    }.start()
            }
        }

    }

    fun uptime(){
        ok_time.setText(DateHelper().okTime(str_begin_time,DateHelper().now()))
    }
}
