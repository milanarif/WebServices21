module core {
    uses spi.WeatherData;
    requires com.google.gson;
    requires persistence;
    requires spi;
}