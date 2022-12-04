package com.example.dynamiclink

import android.content.Context
import android.content.Intent
import androidx.core.app.ShareCompat

const val inviteLink = "https://invitecgs.page.link"
const val previewImageUrl =
    "https://images.pexels.com/photos/9336141/pexels-photo-9336141.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"

fun Context.shareLink(deepLink: String) {
    /*ShareCompat.IntentBuilder(this)
        .setType("text/plain")
        .setChooserTitle("Chooser title")
        .setText(deepLink)
        .startChooser()*/
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, deepLink)
        putExtra(Intent.EXTRA_TITLE, "Come join my team folks")
        putExtra("Tid","622")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}
