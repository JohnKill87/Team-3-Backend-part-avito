package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.AdEntity;

import java.util.Collection;

public interface AdRepository extends JpaRepository<AdEntity, Integer> {

}