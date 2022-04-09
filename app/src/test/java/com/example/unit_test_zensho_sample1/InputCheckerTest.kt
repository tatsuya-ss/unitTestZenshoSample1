package com.example.unit_test_zensho_sample1

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.IllegalArgumentException

@RunWith(JUnit4::class)
class InputCheckerTest {
    lateinit var target: InputChecker

    @Before
    fun setUp() {
        target = InputChecker()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_3文字未満の場合falseを返すこと() {
        val actual = target.isValid("ab")
        assertEquals(actual, false)
    }

    @Test
    fun test_アルファベット３文字以上の時trueを返すこと() {
        val actual = target.isValid("abc")
        assertEquals(actual, true)
    }

    @Test
    fun test_数字３文字以上の時trueを返すこと() {
        val actual = target.isValid("123")
        assertEquals(actual, true)
    }

    @Test
    fun test_3文字以上で英字と数字の組み合わせの時trueを返すこと() {
        val actual = target.isValid("abc123")
        assertEquals(actual, true)
    }

    @Test
    fun test_英字と数字以外の文字が入っているとfalseを返すこと() {
        val actual = target.isValid("abc@123")
        assertEquals(actual, false)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_nullの時にIllegalArgumentExceptionを投げること() {
        target.isValid(null)
    }

//    @Ignore("テスト対象が仮実装なので一時的にスキップ")
//    @Test
//    fun test_一時スキップ() {
//        target.isValid("")
//    }
}