package com.fiap.mercadoexpress.controller;

import com.fiap.mercadoexpress.assembler.MercadoModelAssembler;
import com.fiap.mercadoexpress.exception.MercadoNotFoundException;
import com.fiap.mercadoexpress.model.Mercado;
import com.fiap.mercadoexpress.repository.MercadoRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller REST responsavel pelo CRUD do "mercado express".
 *
 * Endpoint base: /mercado
 * Porta do Tomcat: 8082 (definida em application.properties)
 *
 * Fluxo (conforme diagrama do enunciado):
 * Postman/Insomnia --http--> Controller --> Repository (EntityManager) --> Banco Oracle (TDS_TB_mercado)
 */
@RestController
@RequestMapping(value = "/mercado", produces = MediaType.APPLICATION_JSON_VALUE)
public class MercadoController {

    private final MercadoRepository repository;
    private final MercadoModelAssembler assembler;

    public MercadoController(MercadoRepository repository, MercadoModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // ---------- READ (listar todos) ----------
    // GET http://localhost:8082/mercado
    @GetMapping
    public CollectionModel<EntityModel<Mercado>> listarTodos() {
        List<EntityModel<Mercado>> produtos = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(produtos,
                linkTo(methodOn(MercadoController.class).listarTodos()).withSelfRel());
    }

    // ---------- READ (buscar por id) ----------
    // GET http://localhost:8082/mercado/{id}
    @GetMapping("/{id}")
    public EntityModel<Mercado> buscarPorId(@PathVariable Long id) {
        Mercado mercado = repository.findById(id)
                .orElseThrow(() -> new MercadoNotFoundException(id));
        return assembler.toModel(mercado);
    }

    // ---------- CREATE ----------
    // POST http://localhost:8082/mercado
    // Body (JSON):
    // {
    //   "nome": "Detergente Neutro 500ml",
    //   "tipo": "Limpeza",
    //   "setor": "Higiene",
    //   "tamanho": "500ml",
    //   "preco": 3.99
    // }
    @PostMapping
    public ResponseEntity<EntityModel<Mercado>> criar(@Valid @RequestBody Mercado novoMercado) {
        Mercado salvo = repository.save(novoMercado);
        EntityModel<Mercado> model = assembler.toModel(salvo);
        return ResponseEntity
                .created(model.getRequiredLink("self").toUri())
                .body(model);
    }

    // ---------- UPDATE (completo) ----------
    // PUT http://localhost:8082/mercado/{id}
    // Body (JSON): mesma estrutura do POST, com todos os campos preenchidos
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Mercado>> atualizar(@PathVariable Long id, @RequestBody Mercado novosDados) {
        Mercado atualizado = repository.findById(id)
                .map(mercado -> {
                    mercado.setNome(novosDados.getNome());
                    mercado.setTipo(novosDados.getTipo());
                    mercado.setSetor(novosDados.getSetor());
                    mercado.setTamanho(novosDados.getTamanho());
                    mercado.setPreco(novosDados.getPreco());
                    return repository.save(mercado);
                })
                .orElseThrow(() -> new MercadoNotFoundException(id));

        return ResponseEntity.ok(assembler.toModel(atualizado));
    }

    // ---------- UPDATE (parcial) ----------
    // PATCH http://localhost:8082/mercado/{id}
    // Body (JSON): apenas os campos que deseja alterar, por exemplo:
    // { "preco": 4.49 }
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Mercado>> atualizarParcial(@PathVariable Long id, @RequestBody Mercado camposParciais) {
        Mercado mercado = repository.findById(id)
                .orElseThrow(() -> new MercadoNotFoundException(id));

        if (camposParciais != null) {
            if (camposParciais.getNome() != null) mercado.setNome(camposParciais.getNome());
            if (camposParciais.getTipo() != null) mercado.setTipo(camposParciais.getTipo());
            if (camposParciais.getSetor() != null) mercado.setSetor(camposParciais.getSetor());
            if (camposParciais.getTamanho() != null) mercado.setTamanho(camposParciais.getTamanho());
            if (camposParciais.getPreco() != null) mercado.setPreco(camposParciais.getPreco());
        }

        Mercado salvo = repository.save(mercado);
        return ResponseEntity.ok(assembler.toModel(salvo));
    }

    // ---------- DELETE ----------
    // DELETE http://localhost:8082/mercado/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new MercadoNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
