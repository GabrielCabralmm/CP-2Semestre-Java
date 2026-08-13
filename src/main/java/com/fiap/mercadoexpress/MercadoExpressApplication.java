package com.fiap.mercadoexpress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Checkpoint 4 - Parte 1 (API e Deploy)
 * FIAP - Tecnologia em Analise e Desenvolvimento de Sistemas (TDS)
 *
 * API REST para uma empresa do tipo "mercado express", com persistencia
 * em banco Oracle (ORACLE_FIAP), utilizando Lombok e HATEOAS (nivel de
 * maturidade 3 - Richardson Maturity Model).
 */
@SpringBootApplication
public class MercadoExpressApplication {

    public static void main(String[] args) {
        SpringApplication.run(MercadoExpressApplication.class, args);
    }

}
