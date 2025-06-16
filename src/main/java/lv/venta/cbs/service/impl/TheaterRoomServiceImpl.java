package lv.venta.cbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.cbs.model.TheaterRoom;
import lv.venta.cbs.repository.TheaterRoomRepository;
import lv.venta.cbs.service.TheaterRoomService;

@Service
public class TheaterRoomServiceImpl implements TheaterRoomService {

    private final TheaterRoomRepository roomRepository;

    public TheaterRoomServiceImpl(TheaterRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<TheaterRoom> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public TheaterRoom getRoomById(int id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }

    @Override
    public TheaterRoom createRoom(TheaterRoom room) {
        return roomRepository.save(room);
    }

    @Override
    public TheaterRoom updateRoom(TheaterRoom room) {
        if (!roomRepository.existsById(room.getRoomId())) {
            throw new RuntimeException("Room not found with id: " + room.getRoomId());
        }
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(int id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }
} 