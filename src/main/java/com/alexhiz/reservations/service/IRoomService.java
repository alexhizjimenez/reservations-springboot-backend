package com.alexhiz.reservations.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alexhiz.reservations.model.Room;

public interface IRoomService extends ICRUD<Room, UUID> {

    Page<Room> listPage(Pageable pageable);

}
