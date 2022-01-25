package com.smaato.requestprocessor.repository;

import com.smaato.requestprocessor.data.UniqueRequestsPerMinute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<UniqueRequestsPerMinute, Long> {}
