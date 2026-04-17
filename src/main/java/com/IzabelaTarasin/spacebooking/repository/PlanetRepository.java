package com.IzabelaTarasin.spacebooking.repository;

import com.IzabelaTarasin.spacebooking.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, UUID> { //extends JpaRepository dzieki temu SpringData JPA podepnie cala logike bazy danych
   //gdyby byla to klasa nalezaloby pisac caly kod recznie do obslugi bazy danych (otwieranie połączenia, zapytania SQL, mapowanie wyników)
    // Zastosowałam interfejsy dla repozytoriów, ponieważ Spring Data JPA automatycznie dostarcza ich implementacji w czasie wykonywania programu. Dzięki temu unikam pisania powtarzalnego kodu (boilerplate), a moja aplikacja jest bardziej elastyczna i łatwiejsza do testowania dzięki abstrakcji."

    //Do zarządzania listą dostępnych planet (celów podróży). Pozwala np. pobrać listę wszystkich planet, na które można polecieć.

    Optional<Planet> findByName(String name);
    List<Planet> findByGalaxy(String galaxy);
    boolean existsByName(String name);
}
