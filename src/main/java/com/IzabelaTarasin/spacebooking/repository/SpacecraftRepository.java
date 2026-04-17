package com.IzabelaTarasin.spacebooking.repository;

import com.IzabelaTarasin.spacebooking.model.Spacecraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpacecraftRepository extends JpaRepository<Spacecraft, UUID> {
    //zarządzać flotą statków kosmicznych (sprawdzać ich stan techniczny, pojemność, dostępność).
}
