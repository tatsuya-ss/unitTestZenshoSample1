package com.example.unit_test_zensho_sample1


class WeatherForecast(
    val satellite: Satellite,
    val recorder: WeatherRecorder,
    val formatter: WeatherFormatter,
) {  // テストケースからスタブ差し替えられるようにコンストラクタ引数を受け取る
    fun shouldBringUmbrella(): Boolean {
        val weather = satellite.getWeather()
        return when (weather) {
            Weather.SUNNY, Weather.CLOUDY -> false
            Weather.RAINY -> true
        }
    }

    fun recordCurrentWeather() {
        val weather = satellite.getWeather()
        val formatted = formatter.format(weather)
        recorder.record(formatted)
    }
}

enum class Weather {
    SUNNY, CLOUDY, RAINY
}

// Satelliteのスタブ化
open class Satellite {  // openにしてサブクラスからのオーバーライドを可能にする。
    open fun getWeather(): Weather {
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
