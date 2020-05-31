package kata.supermarket;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static kata.supermarket.Discount.Type.BY_COUNT;
import static kata.supermarket.Discount.Type.PERCENTAGE;
import static kata.supermarket.Discount.Type.STATIC_PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class DiscountTest {

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(null, "Discount type is null"),
                Arguments.of(BY_COUNT, "BY_COUNT has not configured properly: freeCount is null and appliesAtAmount is null"),
                Arguments.of(PERCENTAGE, "PERCENTAGE has not configured properly: value is null"),
                Arguments.of(STATIC_PRICE, "STATIC_PRICE has not configured properly: value is null and appliesAtAmount is null"));
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void throwExceptionIfDataIsMissing(Discount.Type type, String expectedExceptionMessage) {
        Discount discount = new Discount().type(type);

        Throwable thrown = catchThrowable(discount::validate);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedExceptionMessage);
    }
}