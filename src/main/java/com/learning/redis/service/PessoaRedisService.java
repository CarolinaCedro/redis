package com.learning.redis.service;

import com.learning.redis.entities.Pessoa;
import com.learning.redis.entities.PessoaRedis;
import com.learning.redis.repository.PessoaRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaRedisService {

    private final PessoaRedisRepository repository;
    public PessoaRedis created(PessoaRedis request) {
        return repository.save(request);
    }

    public List<PessoaRedis> getAll() {
        return (List<PessoaRedis>) repository.findAll();
    }
}
