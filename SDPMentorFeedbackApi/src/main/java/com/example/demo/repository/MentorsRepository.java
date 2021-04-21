package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Mentors;

@Repository
public interface MentorsRepository extends MongoRepository<Mentors, Long> {

	Mentors getByusername(String username);

}
