package com.jevely.tellu

import android.graphics.Point


//获取屏幕高宽
fun getScreen(): Point {
    val dm = TellUApplication.getContext().getResources().getDisplayMetrics()
    val heigth = dm.heightPixels
    val width = dm.widthPixels
    val point = Point(heigth, width)
    return point
}

//dp2px
fun dp2px(dipValue: Float): Int {
    val scale = TellUApplication.getContext().getResources().getDisplayMetrics().density
    return (dipValue * scale + 0.5f).toInt()
}

//px2dp
fun px2dp(pxValue: Float): Int {
    val scale = TellUApplication.getContext().getResources().getDisplayMetrics().density
    return (pxValue / scale + 0.5f).toInt()
}