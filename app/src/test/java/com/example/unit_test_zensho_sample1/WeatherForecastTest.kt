package com.example.unit_test_zensho_sample1

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.assertj.core.api.Assertions.*

class WeatherForecastTest {
    lateinit var weatherForecast: WeatherForecast

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun shouldBringUmbrella() {
        val satellite = StubSatellite(Weather.SUNNY)
        weatherForecast = WeatherForecast(satellite = satellite)
        val actual = weatherForecast.shouldBringUmbrella()
        assertThat(actual).isFalse()
    }
}