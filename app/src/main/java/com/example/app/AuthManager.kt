package com.example.app

class AuthManager {

    /**
     * ユーザー名とパスワードでログイン処理を行います。
     * (このサンプルでは単純なチェックのみ)
     *
     * @param username ユーザー名
     * @param password パスワード
     * @return ログイン成功ならtrue、失敗ならfalse
     */
    fun login(username: String, password: String): Boolean {
        return username == "testuser" && password == "password"
    }

    /**
     * 入力値のバリデーションを行います。
     * ユーザー名とパスワードが空でないことを確認します。
     *
     * @param username ユーザー名
     * @param password パスワード
     * @return 入力が有効ならtrue、無効ならfalse
     */
    fun validateInput(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }
}
