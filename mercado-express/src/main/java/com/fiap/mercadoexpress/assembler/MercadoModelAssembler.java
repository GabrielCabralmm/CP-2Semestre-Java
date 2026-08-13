package com.fiap.mercadoexpress.assembler;

import com.fiap.mercadoexpress.controller.MercadoController;
import com.fiap.mercadoexpress.model.Mercado;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Responsavel por converter a entidade Mercado em um EntityModel<Mercado>,
 * adicionando os hiperlinks (self, listar, atualizar, deletar).
 *
 * Isso implementa o nivel de maturidade 3 do Modelo de Maturidade de
 * Richardson (HATEOAS): cada recurso retornado traz os links das acoes
 * possiveis, permitindo que o cliente "navegue" pela API.
 */
@Component
public class MercadoModelAssembler implements RepresentationModelAssembler<Mercado, EntityModel<Mercado>> {

    @Override
    public EntityModel<Mercado> toModel(Mercado mercado) {
        return EntityModel.of(mercado,
                linkTo(methodOn(MercadoController.class).buscarPorId(mercado.getId())).withSelfRel(),
                linkTo(methodOn(MercadoController.class).listarTodos()).withRel("mercado"),
                linkTo(methodOn(MercadoController.class).atualizar(mercado.getId(), null)).withRel("atualizar"),
                linkTo(methodOn(MercadoController.class).atualizarParcial(mercado.getId(), null)).withRel("atualizar-parcial"),
                linkTo(methodOn(MercadoController.class).deletar(mercado.getId())).withRel("deletar")
        );
    }

}
