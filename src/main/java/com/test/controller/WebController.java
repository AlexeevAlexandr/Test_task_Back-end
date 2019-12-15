package com.test.controller;

import com.test.entity.Conference;
import com.test.entity.ConferenceParticipant;
import com.test.entity.ConferenceRoom;
import com.test.service.ConferenceRoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class WebController extends ControllerHelper{
    private final ConferenceController conferenceController;
    private final ConferenceRoomController conferenceRoomController;
    private final ConferenceRoomService conferenceRoomService;

    public WebController(ConferenceController conferenceController, ConferenceRoomController conferenceRoomController, ConferenceRoomService conferenceRoomService) {
        this.conferenceController = conferenceController;
        this.conferenceRoomController = conferenceRoomController;
        this.conferenceRoomService = conferenceRoomService;
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

    @PostMapping(value = "/deleteConference")
    public String deleteConference(String[] data) {
        conferenceController.delete(data[0]);
        return "redirect:/conferences";
    }

    @GetMapping("/participants")
    public String participants(Model model) {
        model.addAttribute("participants", conferenceController.getById(id).getParticipants());
        return "participants";
    }

    @PostMapping("/participants")
    public String participants(String[] conferenceId, Model model) {
        id = conferenceId[0];
        conference = conferenceController.getById(id);
        model.addAttribute("participants", conference.getParticipants());
        return "participants";
    }

    @PostMapping("/deleteParticipant")
    public String deleteParticipant(String[] participant) {
        List<ConferenceParticipant> participants = conference.getParticipants();
        for (ConferenceParticipant p : participants) {
            if (p.getBirthDate().toString().equals(participant[1]) && p.getFullName().equals(participant[0])) {
                participants.remove(p);
                break;
            }
        }
        conference.setParticipants(participants);
        conferenceController.update(conference);
        return "redirect:/participants";
    }

    @GetMapping("/conferenceRooms")
    public String conferenceRooms(Model model) {
        model.addAttribute("conferenceRooms", conferenceRoomController.getAll());
        return "conferenceRooms";
    }

    @GetMapping("/addConferenceRoom")
    public String addConferenceRoom(Model model) {
        ConferenceRoom conferenceRoom = new ConferenceRoom();
        model.addAttribute("conferenceRoom", conferenceRoom);
        return "addConferenceRoom";
    }

    @PostMapping("/addConferenceRoom")
    public String addConferenceRoom(@ModelAttribute("conferenceParticipant") ConferenceRoom conferenceRoom) {
        conferenceRoomController.create(conferenceRoom);
        return "redirect:/conferenceRooms";
    }

    @PostMapping("/selectRoom")
    public String selectRoom(String[] id, Model model) {
        conference = conferenceController.getById(id[0]);
        model.addAttribute("conferenceRooms", conferenceRoomService.findByIdGreaterThanEqual(conference.getParticipants().size()));
        return "selectRoom";
    }

    @PostMapping("/acceptRoom")
    public String acceptRoom(String[] roomId) {
            ConferenceRoom conferenceRoom = conferenceRoomController.getById(roomId[0]);
            conference.setConferenceRoom(conferenceRoom);
            conferenceController.update(conference);
        return "redirect:/conferences";
    }
}
