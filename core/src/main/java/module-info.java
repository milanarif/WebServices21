import spi.Random;

module core {
    requires com.google.gson;
    uses Random;
    requires spi;
    requires persistence;
}