package com.example.app

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * AuthManagerクラスの単体テスト。
 * このテストは、AuthManagerの個々のメソッドが期待通りに動作するかを検証します。
 * 依存関係はなく、JVM上で直接実行できます。
 */
class AuthManagerTest {

    private val authManager = AuthManager()

    @Test
    fun `validateInput - 正常系 - ユーザー名とパスワードが有効な場合`() {
        // 期待値: true (バリデーション成功)
        // 実際の動作: authManager.validateInput に有効な値を渡す
        assertTrue("ユーザー名とパスワードが有効な場合はtrueであるべき", authManager.validateInput("testuser", "password"))
    }

    @Test
    fun `validateInput - 異常系 - ユーザー名が空の場合`() {
        // 期待値: false (バリデーション失敗)
        // 実際の動作: authManager.validateInput に空のユーザー名を渡す
        assertFalse("ユーザー名が空の場合はfalseであるべき", authManager.validateInput("", "password"))
    }

    @Test
    fun `validateInput - 異常系 - パスワードが空の場合`() {
        // 期待値: false (バリデーション失敗)
        // 実際の動作: authManager.validateInput に空のパスワードを渡す
        assertFalse("パスワードが空の場合はfalseであるべき", authManager.validateInput("testuser", ""))
    }

    @Test
    fun `validateInput - 異常系 - ユーザー名とパスワードが両方空の場合`() {
        // 期待値: false (バリデーション失敗)
        // 実際の動作: authManager.validateInput に両方空の値を渡す
        assertFalse("ユーザー名とパスワードが両方空の場合はfalseであるべき", authManager.validateInput("", ""))
    }

    // login メソッドのテストも同様に追加できます
    @Test
    fun `login - 正常系 - 正しい認証情報`() {
        assertTrue(authManager.login("testuser", "password"))
    }

    @Test
    fun `login - 異常系 - 間違ったユーザー名`() {
        assertFalse(authManager.login("wronguser", "password"))
    }

    @Test
    fun `login - 異常系 - 間違ったパスワード`() {
        assertFalse(authManager.login("testuser", "wrongpassword"))
    }
}
