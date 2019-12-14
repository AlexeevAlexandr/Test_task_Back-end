package com.test.controller;

import com.test.entity.Conference;
import com.test.entity.ConferenceParticipant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class WebController {
    private final ConferenceController conferenceController;

    public WebController(ConferenceController conferenceController) {
        this.conferenceController = conferenceController;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/conferences")
    public String conferences(Model model) {
        model.addAttribute("conferences", conferenceController.getAll());
        ConferenceParticipant conferenceParticipant = new ConferenceParticipant();
        model.addAttribute("conferenceParticipant", conferenceParticipant);
        return "conferences";
    }

    @GetMapping("/register")
    public String register(Model model) {
        Conference conference = new Conference();
        model.addAttribute("conference", conference);
        return "register";
    }

    @PostMapping(value = "/conferences")
    public String createOrder(@ModelAttribute("conferenceParticipant") ConferenceParticipant conferenceParticipant, String[] data) {
        String id = data[0];
        Conference conference = conferenceController.getById(id);
        List<ConferenceParticipant> participants = conference.getParticipants();
        participants.add(conferenceParticipant);
        conference.setParticipants(participants);
        conferenceController.update(conference);
        return "redirect:/register";
    }

    @GetMapping("/addConference")
    public String addConference(Model model) {
        Conference conference = new Conference();
        model.addAttribute("conference", conference);
        return "addConference";
    }

    @PostMapping(value = "/addConference")
    public String addConference(String[] data) {
        conferenceController.create(new Conference(data[0],
                LocalDateTime.parse(data[1].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        return "redirect:/conferences";
    }

    @PostMapping(value = "/activity")
    public String changeActivity(String[] status) {
        Conference conference = conferenceController.getById(status[0]);
        conference.setStatus(status[1]);
        conferenceController.update(conference);
        return "redirect:/conferences";
    }
}
