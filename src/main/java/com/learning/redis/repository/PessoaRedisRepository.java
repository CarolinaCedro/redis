package com.learning.redis.repository;

import com.learning.redis.entities.Pessoa;
import com.learning.redis.entities.PessoaRedis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRedisRepository extends CrudRepository<PessoaRedis,String> {
}
