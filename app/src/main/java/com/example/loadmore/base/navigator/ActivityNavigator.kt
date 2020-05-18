package com.example.loadmore.base.navigator

import android.app.Activity

class ActivityNavigator constructor(private val activity: Activity) : Navigator() {

    override fun getActivity(): Activity = activity
}