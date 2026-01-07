package com.IzabelaTarasin.spacebooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity //klasa jest encja JPA
public class SpaceFlightBooking {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)  //dzieki temu system sam nada kolejny numery ID, nie trzeba recznie.
    // Tabele bedzie miec kolumnę AUTO_INCREMENT, nr ID bez powtorzen
    //opcja ta nie nadaje się do duzych paczek danych
    @UuidGenerator(style = UuidGenerator.Style.TIME) //zamiast @GeneratedValue, dodanie TIME powoduje ze nowe rekordy zawsze na koncu (przyspiesza baze)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    private BigDecimal finalPrice; //big decimal bo przechowywanie liczny jakby tekstem i dzieki temu zawsze 0.1+0.2 = 0.3, dla zmiennoprzecinkowych typu double lub float
    //liczby zmiennoprzecinkowe pzrechowywane w sytemie binarnym przez co 0.1 + 0.2, wynik może wynieść 0.30000000000000004
    //w bazie mapuje się do DECIMAL
    private PaymentStatus paymentStatus;
}
