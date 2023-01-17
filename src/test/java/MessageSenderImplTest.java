import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.Map;
import java.util.stream.Stream;


public class MessageSenderImplTest {
    @ParameterizedTest
    @MethodSource("messageSenderSource")
    void test_message_sender_send_mockito(Map<String, String> headers, String expected) {

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("172.168.0.1")).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(geoService.byIp("96.168.0.1")).thenReturn(new Location("New York", Country.USA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(geoService.byIp("172.168.0.1").getCountry())).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(geoService.byIp("96.168.0.1").getCountry())).thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String actually = messageSender.send(headers);

        Assertions.assertEquals(expected, actually);
    }

    public static Stream<Arguments> messageSenderSource() {
        return Stream.of(
                Arguments.of(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "172.168.0.1"), "Добро пожаловать"),
                Arguments.of(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "96.168.0.1"), "Welcome")
        );
    }


}
