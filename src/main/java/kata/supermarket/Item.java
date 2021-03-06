package kata.supermarket;

import java.math.BigDecimal;

public interface Item {

    BigDecimal price();

    Discount discount();

    Product product();
}
