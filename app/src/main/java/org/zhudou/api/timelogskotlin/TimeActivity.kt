package org.zhudou.api.timelogskotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_time.*

class TimeActivity : AppCompatActivity() {

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
                val str_begin_time = c.getString(c.getColumnIndex("begin_time"))
                val str_end_time = c.getString(c.getColumnIndex("end_time"))
                begin_time.setText(str_begin_time)
                finish.setOnClickListener(View.OnClickListener {
                    var str_doing=doing.text
                    dbHelper.exec("update logs set title='$str_doing',end_time=datetime('now','localtime'),status='finish' where _id='$str_id'")
                    val intent = Intent(this@TimeActivity, MainActivity::class.java)
                    startActivity(intent)
                })
            }
        }

    }
}
