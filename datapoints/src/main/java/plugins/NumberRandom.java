package plugins;

import spi.Adress;
import spi.Random;

@Adress("number")
public class NumberRandom implements Random {
    @Override
    public String getRandom() {
        Integer random = (int)(Math.random() * (1000));
        return random.toString();
    }
}
