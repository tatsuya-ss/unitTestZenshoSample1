package com.example.unit_test_zensho_sample1

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.IllegalArgumentException
import org.assertj.core.api.Assertions.*
import org.assertj.core.api.SoftAssertions
import java.lang.Exception
import java.lang.RuntimeException

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
        assertThat(actual).isTrue()
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

    @Test
    fun test_文字のアサーション() {
        assertThat("TOKYO")
            .`as`("TEXT CHECK TOKYO")
            .isEqualTo("TOKYO")
            .isEqualToIgnoringCase("tokyo")
            .isNotEqualTo("KYOTO")
            .isNotBlank()
            .startsWith("TO")
            .endsWith("YO")
            .contains("OKY")
            .matches("[A-Z]{5}")
            .isInstanceOf(String::class.java)
    }

    @Test
    fun test_文字のソフトアサーション() {
        SoftAssertions().apply {
            assertThat("TOKYO")
                .`as`("TEXT CHECK TOKYO")
                .isEqualTo("KYOTO")
                .isEqualToIgnoringCase("tokyo")
                .isNotEqualTo("KYOTO")
                .isNotBlank()
                .startsWith("TO")
                .endsWith("YO")
                .contains("OKY")
                .matches("[A-Z]{5}")
                .isInstanceOf(String::class.java)
        }.assertAll() // ...(4)
    }

//    @Ignore("テスト対象が仮実装なので一時的にスキップ")
//    @Test
//    fun test_一時スキップ() {
//        target.isValid("")
//    }

    @Test
    fun test_リスト() {
        val target = listOf("Giants", "Dodgers", "Athletics")
        assertThat(target)
            .hasSize(3)
            .contains("Dodgers")
            .containsOnly("Athletics", "Giants", "Dodgers")
            .containsExactly("Giants", "Dodgers", "Athletics")
            .doesNotContain("Padres")
    }
}

class TeamTest {
    data class BallTeam(
        val name: String,
        val city: String,
        val stadium: String,
    )

    val target = listOf(
        BallTeam("Giants", "San Francisco", "AT&T Park"),
        BallTeam("Dodgers", "Los Angels", "Dodger Stadium"),
        BallTeam("Angels", "Los Angels", "Angel Stadium"),
        BallTeam("Athletics", "Oakland", "Oakland Coliseum"),
        BallTeam("Padres", "San Diego", "Petco Park"),
    )

    @Test
    fun test_フィルタ() {
        assertThat(target)
            .filteredOn { team -> team.city.startsWith("San") }
            .filteredOn { team -> team.city.endsWith("Francisco") }
            .extracting("name", String::class.java)
            .containsExactly("Giants")

        assertThat(target)
            .filteredOn { team -> team.city == "Los Angels" }
            .extracting("name", "stadium")
            .containsExactly(
                tuple("Dodgers", "Dodger Stadium"),
                tuple("Angels", "Angel Stadium")
            )
    }

    @Test
    fun 例外のテスト() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy { functionMayThrow(null) }
    }

    fun functionMayThrow(text: String?): String {
        if (text == null) throw IllegalArgumentException()
        return "true"
    }
}