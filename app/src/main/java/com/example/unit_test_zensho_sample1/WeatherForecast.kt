package com.example.unit_test_zensho_sample1

//class WeatherForecast {
//    val satellite = Satellite()
//
//    fun shouldBringUmbrella(): Boolean {
//        val weather = satellite.getWeather()
//        return when (weather) {
//            Weather.SUNNY, Weather.CLOUDY -> false
//            Weather.RAINY -> true
//        }
//    }
//}

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

class WeatherForecast(
    val satellite: Satellite
) {  // テストケースからスタブ差し替えられるようにコンストラクタ引数を受け取る
    fun shouldBringUmbrella(): Boolean {
        val weather = satellite.getWeather()
        return when (weather) {
            Weather.SUNNY, Weather.CLOUDY -> false
            Weather.RAINY -> true
        }
    }
}

