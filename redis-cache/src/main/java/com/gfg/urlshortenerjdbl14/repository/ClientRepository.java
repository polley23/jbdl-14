package com.gfg.urlshortenerjdbl14.repository;

import com.gfg.urlshortenerjdbl14.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client,Long> {
    Optional<Client> findByClientName(String name);
}
