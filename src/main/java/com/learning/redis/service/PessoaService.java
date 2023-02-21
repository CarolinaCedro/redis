package com.learning.redis.service;

import com.learning.redis.entities.Pessoa;
import com.learning.redis.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;
    public Pessoa created(Pessoa request) {
        return repository.save(request);
    }

    public List<Pessoa> getAll() {
        return repository.findAll();
    }
}
