package restapi.restapi.events;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
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
    public void javaBean(){
        Event event = new Event();
        String eventName = "Event";
        event.setName(eventName);
        String spring = "Spring";
        event.setDescription(spring);

        assertThat(event.getName()).isEqualTo(eventName);
        assertThat(event.getDescription()).isEqualTo(spring);
    }
}