package com.example.unit_test_zensho_sample1

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.assertj.core.api.Assertions.*
import java.lang.RuntimeException

// Satelliteのスタブ化
//class StubSatellite(val anyWeather: Weather) :
//    Satellite() {  // スタブを作成。任意のWeatherを返すようにコンストラクタ引数で変えられる。
//    override fun getWeather(): Weather {
//        return anyWeather
//    }
//}

// モック化
// テスト対象メソッド実行時に、依存コンポーネントに与える値や挙動の検証
// recordメソッドのテストの際に、recordメソッドの値や挙動の検証？
class MockWeatherRecorder : WeatherRecorder() {
    var weather: Record? = null  // 記録時に渡された天気を記録するプロパティ
    var isCalled = false // メソッドが呼び出されたかを記録するプロパティ

    override fun record(weather: Record) {
        this.weather = weather
        isCalled = true
    }
}

// スパイ化
// 呼び出しの際の引数は何だったのか（入力）や戻り値を記録する
// recordメソッドのテストの際に、format(recordに与える値）の検証
class SpyWeatherFormatter : WeatherFormatter() {
    var weather: Weather? = null
    var isCalled = false

    override fun format(weather: Weather): String {
        this.weather = weather
        isCalled = true
        return super.format(weather)
    }
}

class WeatherForecastTest {
    lateinit var weatherForecast: WeatherForecast
    lateinit var formatter: SpyWeatherFormatter
    lateinit var satellite: Satellite

    @Before
    fun setUp() {
        satellite = mock(name = "MockSatellite")
        whenever(satellite.getWeather(any(), any()))
//            .thenAnswer { invocation ->
//                val latitude = invocation.arguments[0] as Double
//                val longitude = invocation.arguments[1] as Double
//                if (latitude in 20.424086..45.550999 &&
//                    longitude in 122.424086..153.980789) {
//                    return@thenAnswer Weather.SUNNY
//                } else {
//                    return@thenAnswer Weather.RAINY
//                }
//            }
            .thenThrow(RuntimeException("ERROR"))
        val recorder = WeatherRecorder()
        formatter = SpyWeatherFormatter()
        weatherForecast = WeatherForecast(
            satellite = satellite,
            recorder = recorder,
            formatter = formatter,
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun shouldBringUmbrella() {
        val actual = weatherForecast.shouldBringUmbrella(any(), any())
        assertThat(actual).isFalse()
    }

    @Test
    fun recordCurrentWeather_assertCalledCalled() {
        weatherForecast.recordCurrentWeather(any(), any())
        val isCalled = formatter.isCalled
        assertThat(isCalled).isTrue()

        val weather = formatter.weather
        assertThat(weather)
            .isIn(
                Weather.SUNNY,
                Weather.CLOUDY,
                Weather.RAINY
            ) // メソッド呼び出し時に引数として渡されたWeatherオブジェクトを検証
    }

    @Test
    fun shouldBringUmbrella_givenInJapan_returnsFalse() {
        val actual = weatherForecast.shouldBringUmbrella(35.0, 139.0)
        assertThat(actual).isFalse()
    }

    @Test
    fun 傘がいるかのメソッドで例外が発生した時にERRORメッセージを吐くこと() {
        assertThatExceptionOfType(RuntimeException::class.java)
            .isThrownBy {
                weatherForecast.shouldBringUmbrella(1.1, 2.2)
            }
            .withMessage("ERROR")
            .withNoCause()
    }
}

class WeatherRecorderTest {
    lateinit var weatherForecast: WeatherForecast
    lateinit var recorder: WeatherRecorder

    @Before
    fun setUp() {
        recorder = mock(name = "MockRecoder")
        val satellite = Satellite()
        val formatter = WeatherFormatter()
        weatherForecast = WeatherForecast(satellite, recorder, formatter)
    }

    @Test
    fun レコードが呼ばれていること() {
        weatherForecast.recordCurrentWeather(37.0, -122.0)
        verify(recorder, times(1)).record(any())
    }

    @Test
    fun レコードされる引数を検証() {
        weatherForecast.recordCurrentWeather(37.0, -122.2)
        argumentCaptor<Record>().apply {
            verify(recorder, times(1)).record(capture())
            assertThat(firstValue.description).isEqualTo("Weather is RAINY")
        }
    }
}