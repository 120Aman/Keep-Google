package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Node;
@Repository
public interface NodeRepository extends MongoRepository<Node, Long> {

	Node getById(Long nodeId);

}
