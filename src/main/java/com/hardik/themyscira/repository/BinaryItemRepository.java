package com.hardik.themyscira.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.themyscira.entity.BinaryItem;

@Repository
public interface BinaryItemRepository extends JpaRepository<BinaryItem, UUID>{

}
