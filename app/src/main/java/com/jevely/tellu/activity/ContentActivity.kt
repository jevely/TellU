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
import com.jevely.tellu.util.ShareTool
import org.w3c.dom.Text

class ContentActivity : BaseActivity(), View.OnClickListener {

    private lateinit var content_edit: EditText
    private lateinit var title_back: ImageView
    private lateinit var title_yes: ImageView
    private lateinit var content_tip: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        init()
    }

    private fun init() {
        content_edit = findViewById(R.id.content_edit)
        title_back = findViewById(R.id.title_back)
        title_yes = findViewById(R.id.title_yes)
        content_tip = findViewById(R.id.content_tip)

        title_back.setOnClickListener(this)
        title_yes.setOnClickListener(this)
        findViewById<TextView>(R.id.title_title).setText(R.string.content_title)

        ActivityTool.getInstance().addActivity(this)

        if (!ShareTool.getInstance().getBoolean(ShareTool.USER_TIP_3)) {
            setMainTip(R.string.content_tip_write)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.title_back -> finish()
            R.id.title_yes -> {
                ShareTool.getInstance().putBoolean(ShareTool.USER_TIP_3, true)
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

    fun setMainTip(contentID: Int) {
        content_tip.text = resources.getText(contentID)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityTool.getInstance().deleteActivity(this)
    }
}
