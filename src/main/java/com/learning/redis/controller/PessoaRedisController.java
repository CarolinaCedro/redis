package com.learning.redis.controller;

import com.learning.redis.entities.Pessoa;
import com.learning.redis.entities.PessoaRedis;
import com.learning.redis.repository.PessoaRedisRepository;
import com.learning.redis.repository.PessoaRepository;
import com.learning.redis.service.PessoaRedisService;
import com.learning.redis.service.PessoaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v2/redis")
@EnableScheduling
@RequiredArgsConstructor
@Log4j2
public class PessoaRedisController {

    private final int MINUTO = 1000 * 60 ;
    private final long MINUTOS = MINUTO * 1 ;
    private final PessoaRedisService service;
    private final PessoaRepository pessoaRepository;
    private final PessoaRedisRepository pessoaRedisRepository;
    private final ModelMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaRedis create(@RequestBody PessoaRedis request) {
        return service.created(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PessoaRedis> getAll() {
        return service.getAll();
    }

    @Scheduled(fixedDelay = MINUTOS)
    @CacheEvict("list")
    public void sincroniza() {

        System.out.println("Limpou o cache");

        //Obter a lista do banco de dados redis
        List<PessoaRedis> redisList = getAll();

        if (CollectionUtils.isEmpty(redisList)) {
            log.info("Lista vazia ou nula");
        } else {
            //Salvar no banco relacional
            List<Pessoa> pessoaList = new ArrayList<>();
            
            //Para cada pessoa da redisList eu cria uma Pessoa(faço a conversão com modelmapper)
            //e depois salvo na lista
            redisList.stream().forEach(pessoaRedis -> {
                log.info(pessoaRedis);
                Pessoa pessoa = mapper.map(pessoaRedis,Pessoa.class);
                pessoaList.add(pessoa);
            });
            //pessoaService.saveAll();
            pessoaRepository.saveAll(pessoaList);
            //Remover do banco redis
            pessoaRedisRepository.deleteAll(redisList);

        }
    }
}
