package com.twitter.challenge;

import com.twitter.challenge.tools.ConstantsKt;
import com.twitter.challenge.tools.Helper;
import com.twitter.challenge.tools.TemperatureConverter;

import org.assertj.core.data.Offset;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.within;

/**
 * TwitterWeatherModel local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TemperatureConverterTests {
    @Test
    public void testCelsiusToFahrenheitConversion() {
        final Offset<Float> precision = within(0.01f);

        assertThat(TemperatureConverter.celsiusToFahrenheit(-50)).isEqualTo(-58, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(0)).isEqualTo(32, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(10)).isEqualTo(50, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(21.11f)).isEqualTo(70, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(37.78f)).isEqualTo(100, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(100)).isEqualTo(212, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(1000)).isEqualTo(1832, precision);

        assertThat (Helper.Companion.celsiusToFahrenheit (-50)).isEqualTo (-58);

    }
    @Test
    public void testAPI(){
        assertThat(ConstantsKt.TWITTER_API).isEqualTo("http://twitter-code-challenge.s3-website-us-east-1.amazonaws.com/");
    }

    @Test
    public void testTemperature(){
        assertThat (Helper.Companion.celsiusToFahrenheit (-50)).isEqualTo (-58);
        assertThat(Helper.Companion.celsiusToFahrenheit (0)).isEqualTo(32);
        assertThat(Helper.Companion.celsiusToFahrenheit (10)).isEqualTo(50);
        assertThat(Helper.Companion.celsiusToFahrenheit (37.78f)).isEqualTo(100);
        assertThat(Helper.Companion.celsiusToFahrenheit (100)).isEqualTo(212);
        assertThat(Helper.Companion.celsiusToFahrenheit (1000)).isEqualTo(1832);

    }

    @Test
    public void testStandardDeviation(){
        List<Double> weekData = new ArrayList<> ();
        weekData.add (1.0);
        weekData.add (2.0);
        weekData.add (3.0);
        weekData.add (4.0);
        weekData.add (5.0);
                assertThat(Helper.Companion.standardDeviation (weekData)).isEqualTo("1.4142135623730951");

    }
}
