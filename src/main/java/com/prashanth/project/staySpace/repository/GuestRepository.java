package com.prashanth.project.staySpace.repository;

import com.prashanth.project.staySpace.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest,Long> {
}
