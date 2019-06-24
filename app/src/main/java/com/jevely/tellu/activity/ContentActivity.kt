package com.jevely.tellu.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.jevely.tellu.BaseActivity
import com.jevely.tellu.R
import com.jevely.tellu.util.ActivityTool
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
        title_yes = findViewById(R.id.title_yes)

        title_back.setOnClickListener(this)
        title_yes.setOnClickListener(this)
        findViewById<TextView>(R.id.title_title).setText(R.string.content_title)

        ActivityTool.getInstance().addActivity(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.title_back -> finish()
            R.id.title_yes -> {
                val content = content_edit.text.toString()
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this, resources.getString(R.string.content_empty), Toast.LENGTH_SHORT).show()
                    return
                }
                val intent = Intent(this, WatchActivity::class.java)
                intent.putExtra("content", content)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityTool.getInstance().deleteActivity(this)
    }
}
