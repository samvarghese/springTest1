package com.sam.profile.Respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sam.profile.modal.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
