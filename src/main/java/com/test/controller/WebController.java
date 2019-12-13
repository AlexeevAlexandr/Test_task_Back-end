package com.test.controller;

import com.test.entity.Conference;
import com.test.entity.ConferenceParticipant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebController {
    private final ConferenceController conferenceController;
    private Conference conference;

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
        model.addAttribute("conference", conference);
        return "register";
    }

    @PostMapping(value = "/conferences")
    public String createOrder(@ModelAttribute("conferenceParticipant") ConferenceParticipant conferenceParticipant, String[] data) {
        String id = data[0];
        conference = conferenceController.getById(id);
        List<ConferenceParticipant> participants = conference.getParticipants();
        participants.add(conferenceParticipant);
        conference.setParticipants(participants);
        conference = conferenceController.update(conference);
        return "redirect:/register";
    }
}
