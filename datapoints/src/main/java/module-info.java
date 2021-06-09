import plugins.NumberRandom;
import spi.Random;

module temperature.plugins {
    requires spi;
    provides Random with NumberRandom;
    opens plugins to core;
}