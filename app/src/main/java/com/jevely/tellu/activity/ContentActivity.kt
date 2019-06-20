package com.jevely.tellu.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.jevely.tellu.BaseActivity
import com.jevely.tellu.R
import org.w3c.dom.Text

class ContentActivity : BaseActivity(), View.OnClickListener {

    private lateinit var content_edit: EditText
    private lateinit var title_back: ImageView
    private lateinit var title_yes: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        init()
    }

    private fun init() {
        content_edit = findViewById(R.id.content_edit)
        title_back = findViewById(R.id.title_back)
        title_yes = findViewById(R.id.title_back)

        title_back.setOnClickListener(this)
        title_yes.setOnClickListener(this)
        findViewById<TextView>(R.id.title_title).setText(R.string.content_title)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.title_back -> finish()
            R.id.title_yes -> {}
        }
    }
}
