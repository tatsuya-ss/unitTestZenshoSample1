package com.example.unit_test_zensho_sample1


class WeatherForecast(
    val satellite: Satellite,
    val recorder: WeatherRecoder,
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
        recorder.record(weather)
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

class StubSatellite(val anyWeather: Weather) :
    Satellite() {  // スタブを作成。任意のWeatherを返すようにコンストラクタ引数で変えられる。
    override fun getWeather(): Weather {
        return anyWeather
    }
}

open class WeatherRecoder {
    open fun record(weather: Weather) {
        // DBに記録など。。。
    }
}

class MockWeatherRecorder : WeatherRecoder() {
    var weather: Weather? = null  // 記録時に渡された天気を記録するプロパティ
    var isCalled = false // メソッドが呼び出されたかを記録するプロパティ

    override fun record(weather: Weather) {
        this.weather = weather
        isCalled = true
    }
}
