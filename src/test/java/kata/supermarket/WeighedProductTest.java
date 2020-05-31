package kata.supermarket;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class WeighedProductTest {

    @ParameterizedTest
    @MethodSource(value = "itemFromWeighedProductHasExpectedUnitPrice")
    void itemFromWeighedProductHasExpectedUnitPrice(String pricePerKilo, String weightInKilos, BigDecimal expectedPrice) {
        final WeighedProduct weighedProduct = new WeighedProduct(new BigDecimal(pricePerKilo), UUID.randomUUID());

        final Item weighedItem = weighedProduct.weighing(new BigDecimal(weightInKilos));

        assertThat(weighedItem.price()).isEqualByComparingTo(expectedPrice);
    }

    static Stream<Arguments> itemFromWeighedProductHasExpectedUnitPrice() {
        return Stream.of(
                Arguments.of("100.00", "1.00", "100.00"),
                Arguments.of("100.00", "0.33333", "33.33"),
                Arguments.of("100.00", "0.33335", "33.34"),
                Arguments.of("100.00", "0", "0.00")
        );
    }

}