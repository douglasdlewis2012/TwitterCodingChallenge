package com.twitter.challenge.tools


class Helper {


    companion object {
        const val CELSIUS_DEGREE_SYMBOL = "°C"
        const val FAHREINHEIT_DEGREE_SYMBOL = "°F"
        /**
         * Converts temperature in Celsius to temperature in Fahrenheit.
         *
         * @param temperatureInCelsius Temperature in Celsius to convert.
         * @return Temperature in Fahrenheit.
         */
        fun celsiusToFahrenheit(temperatureInCelsius: Float): Float {
            return Math.floor(temperatureInCelsius * 1.8 + 32).toFloat()
        }

        /**
         * Converts temperature in Celsius to temperature in Fahrenheit.
         *
         * @param temperatureInFahrenheit Temperature in Fahrenheit to convert.
         * @return Temperature in Celsius.
         */
        fun fahrenheitToCelsius(temperatureInFahrenheit: Float): Float {
            return (temperatureInFahrenheit - 32) * 5 / 9
        }

        fun averageMeanOfData(weekOfTemperatures: List<Double>): Double {
            var total = 0.00
            for (day in weekOfTemperatures) {
                total += day
            }

            return total!! / weekOfTemperatures.size
        }

        fun standardDeviation(weekOfTemperatures: List<Double>): String {
            val avg = averageMeanOfData(weekOfTemperatures)

            var sumOfMeans = 0.0
            for (day in weekOfTemperatures) {
                val tempTotal = Math.pow(day - avg, 2.0)
                sumOfMeans += tempTotal
            }

            val sumByData = sumOfMeans!! / (weekOfTemperatures.size)
            return Math.sqrt(sumByData).toString()
        }

        fun formatTemperature(temp: String): String {
            return "" + temp + CELSIUS_DEGREE_SYMBOL + "|" + celsiusToFahrenheit(temp.toFloat()) + FAHREINHEIT_DEGREE_SYMBOL
        }
    }
}
