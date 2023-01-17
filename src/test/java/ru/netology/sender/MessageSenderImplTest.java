package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.Map;
import java.util.stream.Stream;


public class MessageSenderImplTest {
    @Mock
    GeoService geoService;
    @Mock
    LocalizationService localizationService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test_message_sender_send_rus_mockito() {
        Mockito.when(geoService.byIp("172.168.0.1")).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        Mockito.when(localizationService.locale(geoService.byIp("172.168.0.1").getCountry())).thenReturn("Добро пожаловать");

        String expected = "Добро пожаловать";

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String actually = messageSender.send(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "172.168.0.1"));

        Assertions.assertEquals(expected, actually);
    }
    @Test
    void test_message_sender_send_us_mockito() {

        Mockito.when(geoService.byIp("96.168.0.1")).thenReturn(new Location("New York", Country.USA, null, 0));

        Mockito.when(localizationService.locale(geoService.byIp("96.168.0.1").getCountry())).thenReturn("Welcome");

        String expected = "Welcome";

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String actually = messageSender.send(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "96.168.0.1"));

        Assertions.assertEquals(expected, actually);
    }



}
