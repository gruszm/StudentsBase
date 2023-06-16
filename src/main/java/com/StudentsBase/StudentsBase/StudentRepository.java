package com.StudentsBase.StudentsBase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Student} entities.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
public interface StudentRepository extends JpaRepository<Student, Long>
{
    /**
     * Retrieves all students associated with a specific subject.
     *
     * @param subject the subject to find associated students
     * @return list of students associated with the subject
     */
    List<Student> findBySubjectsContains(Subject subject);

    /**
     * Retrieves a student by index number.
     *
     * @param indexNumber the index number of the student
     * @return an Optional containing the student if found, or empty if not found
     */
    Optional<Object> findByIndexNumber(String indexNumber);
}
