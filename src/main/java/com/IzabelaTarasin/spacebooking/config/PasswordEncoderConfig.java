package com.IzabelaTarasin.spacebooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration  //ustawienia przy starcie, nie @Component bo component do logiki biznesowej
public class PasswordEncoderConfig {
    @Bean  //metoda tworzy obiekt do wstrzykiwania
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //obiekt klasy z Spring Security, który implementuje PasswordEncoder i w środku używa funkcji haszującej BCrypt
        //encode("hasło") — BCrypt losuje sól, robi x iteracji, zwraca jeden string (np. zaczynający się od $2a$ / $2b$ …),
        // w którym sól i koszt są już wbudowane w ten hash. Ten string zapisuje w bazie
        //matches("hasło", hashZBazy) — ten sam mechanizm porównuje hasło z wejścia z hashem (odszyfrowania nie ma)
        //domyślna sila - 10 (iteracji)
        //https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html
    }
}
