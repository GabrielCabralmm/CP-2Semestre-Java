package com.fiap.mercadoexpress.exception;

/**
 * Excecao lancada quando um produto (Mercado) nao e encontrado pelo Id
 * informado.
 */
public class MercadoNotFoundException extends RuntimeException {

    public MercadoNotFoundException(Long id) {
        super("Produto nao encontrado para o id: " + id);
    }

}
