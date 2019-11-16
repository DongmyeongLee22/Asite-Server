package me.asite.timetable;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class TimetableResource extends Resource<Timetable> {

    public TimetableResource(Timetable timetable, Link... links) {
        super(timetable, links);
        add(linkTo(TimetableController.class).slash(timetable.getId()).withSelfRel());
    }
}
