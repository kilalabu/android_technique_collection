package com.example.app

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowApplication

/**
 * LoginActivityの統合テスト (Robolectricを使用)。
 * このテストは、LoginActivityがAuthManagerと連携し、
 * ユーザー入力と認証ロジックの結果に基づいてUIが正しく更新されるか、
 * および画面遷移が正しく行われるかを検証します。
 * AndroidフレームワークのAPIをモックせずにテスト環境で実行できます。
 */
@RunWith(AndroidJUnit4::class) // Robolectricでテストを実行するために必要
class LoginActivityTest {

    private lateinit var scenario: ActivityScenario<LoginActivity>

    @Before
    fun setUp() {
        // LoginActivityのActivityScenarioを開始
        scenario = ActivityScenario.launch(LoginActivity::class.java)
    }

    @Test
    fun `login - 正常系 - 正しい認証情報でログイン成功しMainActivityに遷移`() {
        scenario.onActivity { activity ->
            // UI要素を取得
            val usernameEditText = activity.findViewById<EditText>(R.id.usernameEditText)
            val passwordEditText = activity.findViewById<EditText>(R.id.passwordEditText)
            val loginButton = activity.findViewById<Button>(R.id.loginButton)

            // ユーザー名とパスワードを入力
            usernameEditText.setText("testuser")
            passwordEditText.setText("password")

            // ログインボタンをクリック
            loginButton.performClick()

            // MainActivityへの遷移を検証
            val expectedIntent = Intent(activity, MainActivity::class.java)
            val actualIntent = ShadowApplication.getInstance().nextStartedActivity
            assertNotNull("MainActivityへのIntentが発行されているべき", actualIntent)
            assertEquals(
                "遷移先のActivityがMainActivityであるべき",
                expectedIntent.component,
                actualIntent.component
            )

            // LoginActivityが終了したことを確認
            assertTrue("LoginActivityは終了しているべき", activity.isFinishing)
        }
    }

    @Test
    fun `login - 異常系 - 間違った認証情報でエラーメッセージ表示`() {
        scenario.onActivity { activity ->
            val usernameEditText = activity.findViewById<EditText>(R.id.usernameEditText)
            val passwordEditText = activity.findViewById<EditText>(R.id.passwordEditText)
            val loginButton = activity.findViewById<Button>(R.id.loginButton)
            val errorTextView = activity.findViewById<TextView>(R.id.errorTextView)

            usernameEditText.setText("wronguser")
            passwordEditText.setText("wrongpassword")
            loginButton.performClick()

            // エラーメッセージが表示されていることを確認
            assertEquals("エラーメッセージが表示されているべき", View.VISIBLE, errorTextView.visibility)
            assertEquals(
                "エラーメッセージの内容が正しいべき",
                "ユーザー名またはパスワードが間違っています",
                errorTextView.text.toString()
            )

            // MainActivityへの遷移が行われていないことを確認
            val actualIntent = ShadowApplication.getInstance().nextStartedActivity
            assertNull("MainActivityへの遷移は行われないべき", actualIntent)
        }
    }

    @Test
    fun `login - 異常系 - 入力バリデーションエラーでメッセージ表示`() {
        scenario.onActivity { activity ->
            val usernameEditText = activity.findViewById<EditText>(R.id.usernameEditText)
            val loginButton = activity.findViewById<Button>(R.id.loginButton)
            val errorTextView = activity.findViewById<TextView>(R.id.errorTextView)

            // ユーザー名のみ入力 (パスワードは空)
            usernameEditText.setText("testuser")
            loginButton.performClick()

            // エラーメッセージが表示されていることを確認
            assertEquals("エラーメッセージが表示されているべき", View.VISIBLE, errorTextView.visibility)
            assertEquals(
                "エラーメッセージの内容が正しいべき",
                "ユーザー名またはパスワードが空です",
                errorTextView.text.toString()
            )
             // MainActivityへの遷移が行われていないことを確認
            val actualIntent = ShadowApplication.getInstance().nextStartedActivity
            assertNull("MainActivityへの遷移は行われないべき", actualIntent)
        }
    }
}
