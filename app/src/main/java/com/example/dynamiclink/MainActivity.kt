package com.example.dynamiclink

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import com.example.dynamiclink.databinding.ActivityMainBinding
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var id = 5
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleDeepLink()

        binding.btnShare.setOnClickListener {
            generateDynamicLink(
                deepLink = "${inviteLink}/team?id=${id++}".toUri(),
                previewImage = previewImageUrl.toUri(),
                getShareableLink = { generatedLink ->
                    this.shareLink(generatedLink)
                }
            )
        }
    }

    private fun generateDynamicLink(
        deepLink: Uri,
        previewImage: Uri,
        getShareableLink: (String) -> Unit = {}
    ) {
        Firebase.dynamicLinks.shortLinkAsync {
            link = deepLink
            domainUriPrefix = inviteLink
            // Required
            androidParameters {
                fallbackUrl = "https://play.google.com/store/apps/details?id=com.codashop".toUri()
                build()
            }
            iosParameters("com.example.ios") {
                setFallbackUrl("https://play.google.com/store/apps/details?id=com.codashop".toUri())
            }
            socialMetaTagParameters {
                title = "Hello Dynamic Testing"
                description = "Please accept the invitation"
                imageUrl = previewImage
            }
        }.addOnSuccessListener {
            getShareableLink.invoke(it.shortLink.toString())

        }.addOnFailureListener {
            Log.d("GenerateLink", "generateSharingLink: ${it.message}}")
        }
    }

    private fun handleDeepLink() {
        FirebaseDynamicLinks.getInstance().getDynamicLink(intent)
            .addOnSuccessListener {
                if (it != null) {
                    val deepLink = it.link
                    deepLink?.let { uri ->
                        Log.d("GenerateLink", "Deep Link: $uri}")
                        /*val path =
                            uri.toString().substring(deepLink.toString().lastIndexOf("/") + 1)*/
                        when {
                            uri.toString().contains("team") -> {
                                // val teamId = path.toInt()
                                val teamId = uri.getQueryParameter("id").orEmpty()
                                Toast.makeText(this, "Your Team Id is $teamId", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                Log.d("GenerateLink", "Deep Link failed: ${it.message}}")
            }

    }
}