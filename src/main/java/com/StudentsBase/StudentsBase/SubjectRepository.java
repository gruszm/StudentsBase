package com.StudentsBase.StudentsBase;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Subject} entities.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
public interface SubjectRepository extends JpaRepository<Subject, Long>
{
    /**
     * Retrieves a subject by its name.
     *
     * @param name the name of the subject
     * @return the subject entity, or null if not found
     */
    Subject findByName(String name);
}
