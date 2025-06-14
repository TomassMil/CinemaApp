package lv.venta.cbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lv.venta.cbs.model.TheaterRoom;
import lv.venta.cbs.service.TheaterRoomService;

@Controller
@RequestMapping("/crud/room")
public class RoomCRUDController {

    private final TheaterRoomService roomService;

    @Autowired
    public RoomCRUDController(TheaterRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String listRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "crud/room/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        TheaterRoom room = new TheaterRoom();
        room.setRoomId(null); // Set to null for auto-generation
        model.addAttribute("room", room);
        return "crud/room/form";
    }

    @PostMapping
    public String createRoom(@ModelAttribute TheaterRoom room, RedirectAttributes redirectAttributes) {
        try {
            roomService.createRoom(room);
            redirectAttributes.addFlashAttribute("success", "Room created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating room: " + e.getMessage());
        }
        return "redirect:/crud/room";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        TheaterRoom room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        return "crud/room/form";
    }

    @PostMapping("/update")
    public String updateRoom(@ModelAttribute TheaterRoom room, RedirectAttributes redirectAttributes) {
        try {
            roomService.updateRoom(room);
            redirectAttributes.addFlashAttribute("success", "Room updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating room: " + e.getMessage());
        }
        return "redirect:/crud/room";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            roomService.deleteRoom(id);
            redirectAttributes.addFlashAttribute("success", "Room deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting room: " + e.getMessage());
        }
        return "redirect:/crud/room";
    }
} 