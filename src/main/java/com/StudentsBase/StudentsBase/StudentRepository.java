package com.StudentsBase.StudentsBase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>
{
    List<Student> findBySubjectsContains(Subject subject);

    Optional<Object> findByIndexNumber(String indexNumber);
}
