package com.jevely.tellu.util

import android.app.Activity

class ActivityTool private constructor() {

    companion object {

        private var activityTool: ActivityTool? = null

        @Synchronized
        fun getInstance(): ActivityTool {
            if (activityTool == null) {
                activityTool = ActivityTool()
            }
            return activityTool as ActivityTool
        }

        fun destroy() {
            activityTool = null
        }

    }

    private val activityList = mutableListOf<Activity>()

    fun addActivity(activity: Activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity)
        }
    }

    fun deleteActivity(activity: Activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity)
        }
    }

    fun finishActivity() {
        activityList.forEach {
            if (!it.isFinishing) {
                it.finish()
            }
        }
        activityList.clear()
    }

}