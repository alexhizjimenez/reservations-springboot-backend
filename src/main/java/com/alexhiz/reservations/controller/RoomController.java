package com.alexhiz.reservations.controller;

import com.alexhiz.reservations.dto.RoomDTO;
import com.alexhiz.reservations.model.Room;
import com.alexhiz.reservations.service.IRoomService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RoomController {

    private final IRoomService roomService;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() throws Exception {
        List<RoomDTO> rooms = roomService.findAll().stream().map(this::convertToDto).toList();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Room>> listPage(Pageable pageable) throws Exception {
        Page<Room> page = roomService.listPage(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable UUID id) throws Exception {
        RoomDTO room = convertToDto(roomService.findById(id));
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO dto) throws Exception {
        Room savedRoom = roomService.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRoom.getIdRoom())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable UUID id, @RequestBody RoomDTO dto) throws Exception {
        Room updatedRoom = roomService.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDto(updatedRoom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable UUID id) throws Exception {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Room convertToEntity(RoomDTO dto) {
        return modelMapper.map(dto, Room.class);
    }

    private RoomDTO convertToDto(Room obj) {
        return modelMapper.map(obj, RoomDTO.class);
    }
}
