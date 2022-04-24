package com.example.unit_test_zensho_sample1

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.assertj.core.api.Assertions.*

// Satelliteのスタブ化
class StubSatellite(val anyWeather: Weather) :
    Satellite() {  // スタブを作成。任意のWeatherを返すようにコンストラクタ引数で変えられる。
    override fun getWeather(): Weather {
        return anyWeather
    }
}

// モック化
// テスト対象メソッド実行時に、依存コンポーネントに与える値や挙動の検証
// recordメソッドのテストの際に、recordメソッドの値や挙動の検証？
class MockWeatherRecorder : WeatherRecorder() {
    var weather: String? = null  // 記録時に渡された天気を記録するプロパティ
    var isCalled = false // メソッドが呼び出されたかを記録するプロパティ

    override fun record(weather: String) {
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
//    lateinit var recorder: MockWeatherRecorder
    lateinit var formatter: SpyWeatherFormatter
    lateinit var satellite: Satellite

    @Before
    fun setUp() {
        satellite = mock(name = "MockSatellite")
        whenever(satellite.getWeather()).thenReturn(Weather.SUNNY)
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
        val actual = weatherForecast.shouldBringUmbrella()
        assertThat(actual).isFalse()
    }

    @Test
    fun recordCurrentWeather_assertCalledCalled() {
        weatherForecast.recordCurrentWeather()
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
}