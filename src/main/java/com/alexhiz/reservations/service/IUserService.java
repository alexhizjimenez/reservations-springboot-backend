package com.alexhiz.reservations.service;

import java.util.UUID;

import com.alexhiz.reservations.model.User;

public interface IUserService extends ICRUD<User, UUID> {

    User findOneByUsername(String username);

    void changePassword(String username, String password);
}
