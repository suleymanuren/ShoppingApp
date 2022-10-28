package com.suleymanuren.shoppingapp

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,
            android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            startActivity(android.content.Intent(this, com.suleymanuren.shoppingapp.AuthActivity::class.java))
            finish()
        }, 3000)
    }
}
