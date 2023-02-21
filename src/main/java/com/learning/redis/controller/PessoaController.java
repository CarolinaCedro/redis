package com.learning.redis.controller;

import com.learning.redis.entities.Pessoa;
import com.learning.redis.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/redis")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService service;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable("list")
    public List<Pessoa> getAll(){
        //Aqui eu coloco só uma verificação para ver quantas vezes o cache é carregado
        System.out.printf("Sem cache");
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa create(@RequestBody Pessoa request){
        System.out.printf("Limpando cache");
        return service.created(request);
    }


}
