package restapi.restapi.events;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import restapi.restapi.common.ErrorsResource;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaTypes.HAL_JSON_VALUE})
public class EventController {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.eventValidator = eventValidator;
    }

    @PostMapping("/api/events")
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {

        if (errors.hasErrors()) {
            return getBadRequest(errors);
        }
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return getBadRequest(errors);
        }
        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        event.checkLocation();
        Event savedEvent = eventRepository.save(event);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(savedEvent.getId());
        URI createdUri = selfLinkBuilder.toUri();
        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withRel("update-event"));
        eventResource.add(new Link("/docs/index.html#resources-events-create").withRel("profile"));
        return ResponseEntity.created(createdUri).body(eventResource);
    }

    /*
        @GetMapping("/api/events")
        public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler) {
            Page<Event> page = this.eventRepository.findAll(pageable);
            PagedModel<EventResource> entityModels = assembler.toModel(page, e -> new EventResource(e));
            entityModels.add(new Link("/docs/index.html#resouces-events-list").withRel("profile"));
            return ResponseEntity.ok(entityModels);
        }*/
    @GetMapping("/api/events/{id}")
    public ResponseEntity getEvent(@PathVariable Long id) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Event event = optionalEvent.get();
        EventResource eventResource = new EventResource(event);
        eventResource.add(new Link("/docs/index.html#resources-events-create").withRel("profile"));
        return ResponseEntity.ok(eventResource);
    }

    @PutMapping("/api/events/{id}")
    public ResponseEntity updateEvent(@PathVariable Long id,
                                      @RequestBody @Valid EventDto eventDto,
                                      Errors errors) {

        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (errors.hasErrors()) {
            return getBadRequest(errors);
        }

        this.eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return getBadRequest(errors);
        }
        Event event = optionalEvent.get();

        modelMapper.map(eventDto, event);
        Event savedEvent = eventRepository.save(event);

        EventResource eventResource = new EventResource(savedEvent);
        eventResource.add(new Link("/docs/index.html#resources-events-update").withRel("profile"));
        return ResponseEntity.ok(eventResource);

    }

    private ResponseEntity<ErrorsResource> getBadRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }

    @PutMapping("/event/{id}")
    public ResponseEntity putEvent(@PathVariable Long id, @RequestBody EventDto eventDto) {

        Optional<Event> byId = eventRepository.findById(id);
        Event event = byId.get();
        event.update();

        return ResponseEntity.ok().body(event);
    }
}

