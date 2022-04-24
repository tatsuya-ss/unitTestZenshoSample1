package com.example.unit_test_zensho_sample1

open class WeatherForecast(
    val satellite: Satellite,
    val recorder: WeatherRecorder,
    val formatter: WeatherFormatter,
) {  // テストケースからスタブ差し替えられるようにコンストラクタ引数を受け取る
    fun shouldBringUmbrella(latitude: Double, longitude: Double): Boolean {
        val weather = satellite.getWeather(latitude, longitude)
        return when (weather) {
            Weather.SUNNY, Weather.CLOUDY -> false
            Weather.RAINY -> true
        }
    }

    fun recordCurrentWeather(latitude: Double, longitude: Double) {
        val weather = satellite.getWeather(latitude, longitude)
        val formatted = formatter.format(weather)
        recorder.record(formatted)
    }
}

enum class Weather {
    SUNNY, CLOUDY, RAINY
}

// Satelliteのスタブ化
open class Satellite {  // openにしてサブクラスからのオーバーライドを可能にする。
    open fun getWeather(latitude: Double, longitude: Double): Weather {
        return Weather.RAINY
    }
}

open class WeatherRecorder {
    open fun record(weather: String) {
        // DBに記録など。。。
    }
}

open class WeatherFormatter {
    open fun format(weather: Weather): String = "Weather is ${weather}"
}
