package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceImplTest {

    @ParameterizedTest
    @MethodSource("localeSource")
    void test_returned_string_by_locale(Country country, String expected) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

        String actually = localizationService.locale(country);

        Assertions.assertEquals(expected, actually);
    }

    public static Stream<Arguments> localeSource(){
        return Stream.of(
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome"),
                Arguments.of(Country.RUSSIA, "Добро пожаловать")

        );
    }
}
