package lv.venta.cbs.service;

import java.util.List;

import lv.venta.cbs.model.TheaterRoom;

public interface TheaterRoomService {
    List<TheaterRoom> getAllRooms();
    TheaterRoom getRoomById(int id);
    TheaterRoom createRoom(TheaterRoom room);
    TheaterRoom updateRoom(TheaterRoom room);
    void deleteRoom(int id);
} 