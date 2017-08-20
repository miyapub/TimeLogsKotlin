package org.zhudou.api.timelogskotlin

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_logs.*
import kotlinx.android.synthetic.main.timelog.*
import kotlinx.android.synthetic.main.timelog.view.*

class LogsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)
        val logs = ArrayList<Map<String, Any>>()
        var dbHelper=DbHelper(this)
        var c=dbHelper.cursor("select * from logs order by _id desc")
        if (c != null) {

            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndex("_id"))
                val title = c.getString(c.getColumnIndex("title"))
                val status = c.getString(c.getColumnIndex("status"))
                val begin_time = c.getString(c.getColumnIndex("begin_time"))
                val end_time = c.getString(c.getColumnIndex("end_time"))

                val map = HashMap<String, Any>()
                map.put("log_id", id);
                map.put("title", title);
                map.put("status", status);
                map.put("begin_time", begin_time);
                map.put("end_time", end_time);
                logs.add(map)

            }
        }
        var simpleAdapter = MyAdapter(baseContext, logs,
                R.layout.timelog, arrayOf("log_id","title","begin_time","end_time"), intArrayOf(R.id.log_id,R.id.title,R.id.begin,R.id.end))
        time_logs.adapter=simpleAdapter
        add.setOnClickListener(View.OnClickListener {
            dbHelper.exec("insert into logs(title,begin_time,end_time,status)values('title',datetime('now','localtime'),datetime('now','localtime'),'doing')")
            val intent = Intent(this@LogsActivity, TimeActivity::class.java)
            startActivity(intent)
        })

        time_logs.onItemLongClickListener= AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
            var id=view.log_id.text
            var title_value=view.title.text
            val alertDialogBuilder = AlertDialog.Builder(this)
            val alertDialog = alertDialogBuilder.create()
            var input_title=EditText(this)
            input_title.setText(title_value)
            alertDialog.setView(input_title)
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"确定", DialogInterface.OnClickListener({ dialogInterface: DialogInterface, i: Int ->
                var str_title=input_title.text
                dbHelper.exec("update logs set title='$str_title' where _id='$id'")
                view.title.setText(str_title)
                simpleAdapter.notifyDataSetChanged()
            }))
            alertDialog.show()
            return@OnItemLongClickListener true
        }

    }
}
