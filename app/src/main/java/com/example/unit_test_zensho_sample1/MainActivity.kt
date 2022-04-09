package com.example.unit_test_zensho_sample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.IllegalArgumentException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class InputChecker {
    fun isValid(text: String?): Boolean {
        if (text == null) throw IllegalArgumentException("Cannot be null")
        return text.length >= 3 && text.matches(Regex("[a-zA-Z0-9]+"))
    }
}
