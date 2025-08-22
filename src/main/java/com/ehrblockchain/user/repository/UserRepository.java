package com.ehrblockchain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ehrblockchain.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

