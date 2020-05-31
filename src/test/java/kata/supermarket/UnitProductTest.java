package kata.supermarket;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UnitProductTest {

    @Test
    void singleItemHasExpectedUnitPriceFromProduct() {
        final BigDecimal price = new BigDecimal("2.49");

        UnitProduct unitProduct = new UnitProduct(price, UUID.randomUUID());

        assertThat(unitProduct.oneOf().price()).isEqualByComparingTo(price);
    }
}