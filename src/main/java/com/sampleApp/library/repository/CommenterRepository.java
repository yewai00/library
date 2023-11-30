package com.sampleApp.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sampleApp.library.model.entity.Commenter;

public interface CommenterRepository extends JpaRepository<Commenter, Long>{

	Optional<Commenter> findByEmail(String email);

}
