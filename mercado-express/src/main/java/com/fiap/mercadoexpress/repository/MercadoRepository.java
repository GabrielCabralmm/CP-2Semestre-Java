package com.fiap.mercadoexpress.repository;

import com.fiap.mercadoexpress.model.Mercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para a entidade Mercado.
 * O EntityManager e todas as operacoes de persistencia (insert, select,
 * update, delete/commit) sao gerenciados automaticamente pelo Spring Data
 * a partir desta interface, que "extende" JpaRepository.
 */
@Repository
public interface MercadoRepository extends JpaRepository<Mercado, Long> {
}
