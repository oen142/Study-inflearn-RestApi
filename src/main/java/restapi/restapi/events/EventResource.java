package restapi.restapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//Bean시리얼라이저
public class EventResource extends EntityModel<Event> {

    //컴포즈 객체
    @JsonUnwrapped
    private Event event;


    public EventResource(Event event) {
        this.event = event;
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());

    }

    public Event getEvent() {
        return event;
    }
}
