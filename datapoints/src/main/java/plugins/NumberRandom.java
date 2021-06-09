package plugins;

import spi.Random;

public class NumberRandom implements Random {
    @Override
    public String getRandom() {
        Integer random = (int)(Math.random() * (1000));
        return random.toString();
    }
}
