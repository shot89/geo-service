import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceImplTest {

    GeoServiceImpl geoService = new GeoServiceImpl();

    @ParameterizedTest
    @MethodSource("geoServiceSource")
    void test_byIp_with_parameters(String ip, Location expected) {
        Location actually = geoService.byIp(ip);

        Assertions.assertEquals(expected.getCountry(), actually.getCountry());
        Assertions.assertEquals(expected.getCity(), actually.getCity());

    }

    public static Stream<Arguments> geoServiceSource() {
        return Stream.of(
                Arguments.of(GeoServiceImpl.LOCALHOST, new Location(null, null, null, 0)),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.0.0.1", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.0.0.1", new Location("New York", Country.USA, null, 0))
        );
    }

    @Test
    void test_location_byCoordinates_runtimeException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    geoService.byCoordinates(0.1, 0.1);
                });
    }

}
