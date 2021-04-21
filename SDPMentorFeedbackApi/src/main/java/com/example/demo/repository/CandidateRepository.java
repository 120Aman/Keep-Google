package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Candidate;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, Long> {

	Candidate getById(Long candidateId);

}
