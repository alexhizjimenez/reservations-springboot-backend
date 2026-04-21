package com.alexhiz.reservations.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alexhiz.reservations.model.Room;
import com.alexhiz.reservations.repo.IGenericRepo;
import com.alexhiz.reservations.repo.IRoomRepo;
import com.alexhiz.reservations.service.IRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl extends CRUDImpl<Room, UUID> implements IRoomService{

    private final IRoomRepo repo;

    @Override
    protected IGenericRepo<Room, UUID> getRepo() {
        return repo;
    }

    @Override
    public Page<Room> listPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

}
