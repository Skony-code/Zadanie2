package com.example.zadanie2.repositories;

import com.example.zadanie2.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
