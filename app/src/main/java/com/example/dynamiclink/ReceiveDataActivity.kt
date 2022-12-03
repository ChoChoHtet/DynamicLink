package com.example.dynamiclink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.Toast
import com.example.dynamiclink.databinding.ActivityReceiveDataBinding
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class ReceiveDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceiveDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiveDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.tvTitle.linksClickable = true
        binding.tvTitle.movementMethod = LinkMovementMethod.getInstance()*/
        handleIncomeLinkData()
    }

    private fun handleIncomeLinkData() {
        if (Intent.ACTION_SEND == intent.action) {
            if ("text/plain" == intent.type) {
                intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                    binding.tvTitle.text = it
                }
            }
        }
    }
}