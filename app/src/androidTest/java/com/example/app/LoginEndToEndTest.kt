package com.example.app

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * ログイン機能のエンドツーエンドテスト (Espressoを使用)。
 * このテストは、ユーザーが実際に操作するのと同じように、
 * UI要素を操作し、アプリケーション全体のフローを検証します。
 * Androidエミュレータまたは実機で実行する必要があります。
 */
@RunWith(AndroidJUnit4::class)
@LargeTest // エンドツーエンドテストは時間がかかるためLargeTestに分類
class LoginEndToEndTest {

    // ActivityScenarioRule を使用して、テストごとにLoginActivityを起動
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun `login_success_navigateToMainActivity`() {
        // 1. ユーザー名を入力
        onView(withId(R.id.usernameEditText))
            .perform(typeText("testuser"), closeSoftKeyboard())

        // 2. パスワードを入力
        onView(withId(R.id.passwordEditText))
            .perform(typeText("password"), closeSoftKeyboard())

        // 3. ログインボタンをクリック
        onView(withId(R.id.loginButton)).perform(click())

        // 4. MainActivityが表示されたことを確認
        // (MainActivity内の特定のViewが表示されているかで判断)
        onView(withText("Welcome to Main Activity!"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `login_failure_showErrorMessage`() {
        // 1. ユーザー名を入力
        onView(withId(R.id.usernameEditText))
            .perform(typeText("wronguser"), closeSoftKeyboard())

        // 2. パスワードを入力
        onView(withId(R.id.passwordEditText))
            .perform(typeText("wrongpassword"), closeSoftKeyboard())

        // 3. ログインボタンをクリック
        onView(withId(R.id.loginButton)).perform(click())

        // 4. エラーメッセージが表示されたことを確認
        onView(withId(R.id.errorTextView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.errorTextView))
            .check(matches(withText("ユーザー名またはパスワードが間違っています")))
    }

    @Test
    fun `login_validationError_showEmptyInputMessage`() {
        // 1. ユーザー名のみ入力 (パスワードは空)
        onView(withId(R.id.usernameEditText))
            .perform(typeText("testuser"), closeSoftKeyboard())

        // 2. ログインボタンをクリック
        onView(withId(R.id.loginButton)).perform(click())

        // 3. バリデーションエラーメッセージが表示されたことを確認
        onView(withId(R.id.errorTextView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.errorTextView))
            .check(matches(withText("ユーザー名またはパスワードが空です")))
    }
}
