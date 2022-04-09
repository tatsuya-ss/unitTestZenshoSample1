package com.example.unit_test_zensho_sample1

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.assertj.core.api.Assertions.*

class WeatherForecastTest {
    lateinit var weatherForecast: WeatherForecast
    lateinit var recorder: MockWeatherRecorder

    @Before
    fun setUp() {
        val satellite = Satellite()
        recorder = MockWeatherRecorder()
        weatherForecast = WeatherForecast(
            satellite = satellite,
            recorder = recorder,
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun shouldBringUmbrella() {
        val satellite = StubSatellite(Weather.SUNNY)
        val recorder = MockWeatherRecorder()
        weatherForecast = WeatherForecast(
            satellite = satellite,
            recorder = recorder,
        )
        val actual = weatherForecast.shouldBringUmbrella()
        assertThat(actual).isFalse()
    }

    @Test
    fun recordCurrentWeather_assertCalled() {
        weatherForecast.recordCurrentWeather()
        val isCalled = recorder.isCalled
        assertThat(isCalled).isTrue()

        val weather = recorder.weather
        assertThat(weather)
            .isIn(
                Weather.SUNNY,
                Weather.CLOUDY,
                Weather.RAINY
            ) // メソッド呼び出し時に引数として渡されたWeatherオブジェクトを検証
    }
}