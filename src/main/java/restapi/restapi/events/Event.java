package restapi.restapi.events;


import lombok.*;
import restapi.restapi.account.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Event {


    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location;
    private int basePrice;
    private int maxPrice;
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Enumerated(value = STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    public void update() {
        if (basePrice == 0 && maxPrice == 0) {
            free = true;
        } else {
            free = false;
        }
    }

    public void checkLocation() {

        if (location.isEmpty()) {
            offline = false;
        } else {
            offline = true;
        }
    }
}
