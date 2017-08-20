package org.zhudou.api.timelogskotlin

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_logs.view.*
import kotlinx.android.synthetic.main.timelog.view.*


class MyAdapter(context: Context, items: ArrayList<Map<String, Any>>, resource: Int, from: Array<String>, to: IntArray) : SimpleAdapter(context, items, resource, from, to) {
    var resource=resource
    var context=context
    var to=to
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view=super.getView(position, convertView, parent)
        view.oktime.setText(DateHelper().okTime(view.begin.text.toString(),view.end.text.toString()))
        return view
    }
}
