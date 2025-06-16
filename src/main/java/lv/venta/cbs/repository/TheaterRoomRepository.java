package lv.venta.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lv.venta.cbs.model.TheaterRoom;

@Repository
public interface TheaterRoomRepository extends JpaRepository<TheaterRoom, Integer> {

	TheaterRoom findByRoomName(String string);
} 