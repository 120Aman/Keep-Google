package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Node;

public interface NodeRepository extends MongoRepository<Node, Long> {

	Node getById(Long nodeId);

}
