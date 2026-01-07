package com.IzabelaTarasin.spacebooking.model;

public enum FlightStatus { //stale/niezmeinne statusy wiec wybieram enum zamiast tabeli w bazie danych
    SCHEDULED,   // Zaplanowany
    BOARDING,    // Odprawa / Wchodzenie na pokład
    IN_ORBIT,    // Na orbicie
    LANDED,      // Wylądował
    CANCELLED    // Odwołany
}
