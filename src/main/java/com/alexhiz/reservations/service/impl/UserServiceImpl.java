package com.alexhiz.reservations.service.impl;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alexhiz.reservations.model.User;
import com.alexhiz.reservations.repo.IGenericRepo;
import com.alexhiz.reservations.repo.IUserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDImpl<User, UUID> implements com.alexhiz.reservations.service.IUserService {

    private final IUserRepo repo;
    private final PasswordEncoder bcrypt;

    @Override
    protected IGenericRepo<User, UUID> getRepo() {
        return repo;
    }

    @Override
    public User findOneByUsername(String username) {
        return repo.findOneByUsername(username);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        repo.changePassword(username, bcrypt.encode(newPassword));
    }
}
