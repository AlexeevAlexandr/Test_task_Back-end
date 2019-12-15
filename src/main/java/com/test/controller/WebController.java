package com.test.controller;

import com.test.entity.Conference;
import com.test.entity.ConferenceParticipant;
import com.test.entity.ConferenceRoom;
import com.test.service.ConferenceRoomService;
import com.test.service.ConferenceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Controller
public class WebController extends ControllerHelper{
    private final ConferenceService conferenceService;
    private final ConferenceRoomService conferenceRoomService;

    public WebController(ConferenceService conferenceService, ConferenceRoomService conferenceRoomService) {
        this.conferenceService = conferenceService;
        this.conferenceRoomService = conferenceRoomService;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/conferences")
    public String conferences(Model model) {
        model.addAttribute("conferences", conferenceService.getAll());
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
        Conference conference = conferenceService.getById(id);
        List<ConferenceParticipant> participants = conference.getParticipants();
        participants.add(conferenceParticipant);
        conference.setParticipants(participants);
        conferenceService.update(conference);
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
        conferenceService.create(new Conference(data[0],
                LocalDateTime.parse(data[1].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        return "redirect:/conferences";
    }

    @PostMapping(value = "/activity")
    public String changeActivity(String[] status) {
        Conference conference = conferenceService.getById(status[0]);
        conference.setStatus(status[1]);
        conferenceService.update(conference);
        return "redirect:/conferences";
    }

    @PostMapping(value = "/deleteConference")
    public String deleteConference(String[] data) {
        conferenceService.delete(data[0]);
        return "redirect:/conferences";
    }

    @GetMapping("/participants")
    public String participants(Model model) {
        model.addAttribute("participants", conferenceService.getById(id).getParticipants());
        return "participants";
    }

    @PostMapping("/participants")
    public String participants(String[] conferenceId, Model model) {
        id = conferenceId[0];
        conference = conferenceService.getById(id);
        List<ConferenceParticipant> conferenceParticipants = conference.getParticipants();
        conferenceParticipants.sort(Comparator.comparing(ConferenceParticipant::getFullName));
        model.addAttribute("participants", conferenceParticipants);
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
        conferenceService.update(conference);
        return "redirect:/participants";
    }

    @GetMapping("/conferenceRooms")
    public String conferenceRooms(Model model) {
        model.addAttribute("conferenceRooms", conferenceRoomService.getAll());
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
        conferenceRoomService.create(conferenceRoom);
        return "redirect:/conferenceRooms";
    }

    @PostMapping("/selectRoom")
    public String selectRoom(String[] id, Model model) {
        conference = conferenceService.getById(id[0]);
        model.addAttribute("conferenceRooms", conferenceRoomService.findByIdGreaterThanEqual(conference.getParticipants().size()));
        return "selectRoom";
    }

    @PostMapping("/acceptRoom")
    public String acceptRoom(String[] roomId) {
        ConferenceRoom conferenceRoom = conferenceRoomService.getById(roomId[0]);
        conference.setConferenceRoom(conferenceRoom);
        conferenceService.update(conference);
    return "redirect:/conferences";
    }
}
