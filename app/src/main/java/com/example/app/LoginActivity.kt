package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var errorTextView: TextView

    // AuthManager のインスタンス (実際のアプリではDIライブラリを使うことが多い)
    private val authManager = AuthManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // activity_login.xml を使用

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        errorTextView = findViewById(R.id.errorTextView)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // 入力バリデーション
            if (!authManager.validateInput(username, password)) {
                errorTextView.text = "ユーザー名またはパスワードが空です"
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }

            // ログイン試行
            if (authManager.login(username, password)) {
                // ログイン成功
                errorTextView.visibility = View.GONE
                startActivity(Intent(this, MainActivity::class.java))
                finish() // LoginActivityを終了
            } else {
                // ログイン失敗
                errorTextView.text = "ユーザー名またはパスワードが間違っています"
                errorTextView.visibility = View.VISIBLE
            }
        }
    }
}
