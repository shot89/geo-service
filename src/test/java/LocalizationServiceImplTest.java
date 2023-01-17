import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

public class LocalizationServiceImplTest {

    @ParameterizedTest
    @EnumSource(Country.class)
    void test_returned_string_by_locale(Country country) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String expected;
        if (country.equals(Country.RUSSIA)) {
            expected = "Добро пожаловать";
        } else expected = "Welcome";

        String actually = localizationService.locale(country);

        Assertions.assertEquals(expected, actually);
    }
}
