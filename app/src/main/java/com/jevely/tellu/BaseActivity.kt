package com.jevely.tellu

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.util.ArrayList

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 检测权限
     *
     * @param permission 权限
     * @return true为有权限   false为没有权限
     */
    fun checkPermission(context: Context, permission: String): Boolean? {
        val selfPermission = ContextCompat.checkSelfPermission(context, permission)
        return selfPermission != PackageManager.PERMISSION_DENIED
    }

    /**
     * 申请权限
     *
     * @param activity    activity
     * @param permissions 权限（支持多个）
     */
    fun requestPermission(activity: Activity, permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    /**
     * 申请权限
     *
     * @param activity activity
     */
    fun requestPermission(activity: Activity, permissions: String, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(permissions), requestCode)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val success = ArrayList<String>()
        val error = ArrayList<String>()
        for (i in permissions.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                error.add(permissions[i])
            } else {
                success.add(permissions[i])
            }
        }
        requestSuccess(requestCode, success)
        requestError(requestCode, error)
    }

    open fun requestSuccess(requestCode: Int, permission: List<String>) {

    }

    open fun requestError(requestCode: Int, permission: List<String>) {

    }
}
