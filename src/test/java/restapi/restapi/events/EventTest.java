package restapi.restapi.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class EventTest {


    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Spring Rest api")
                .description("development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        Event event = new Event();
        String eventName = "Event";
        event.setName(eventName);
        String spring = "Spring";
        event.setDescription(spring);

        assertThat(event.getName()).isEqualTo(eventName);
        assertThat(event.getDescription()).isEqualTo(spring);
    }

    @Test
    public void testFree() {
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        event.update();
        assertThat(event.isFree()).isTrue();

        event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        event.update();
        assertThat(event.isFree()).isFalse();
        event = Event.builder()
                .basePrice(100)
                .maxPrice(100)
                .build();

        event.update();
        assertThat(event.isFree()).isFalse();

    }
    @Test
    public void testOffline(){
        Event event = Event.builder()
                .location("강남")
                .build();

        event.checkLocation();
        assertThat(event.isOffline()).isTrue();
        event = Event.builder()
                .location("")
                .build();

        event.checkLocation();
        assertThat(event.isOffline()).isFalse();
    }
}