package com.StudentsBase.StudentsBase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

/**
 * Repository interface for managing {@link Grade} entities.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
public interface GradeRepository extends JpaRepository<Grade, Long>
{
    /**
     * Retrieves a grade by student's ID and subject's ID.
     *
     * @param studentId the ID of the student
     * @param subjectId the ID of the subject
     * @return an Optional containing the grade if found, or empty if not found
     */
    Optional<Grade> findByStudentIdAndSubjectId(Long studentId, Long subjectId);

    /**
     * Retrieves a list of grades by student's ID.
     *
     * @param studentId the ID of the student
     * @return a list of grades associated with the student's ID
     */
    List<Grade> findByStudentId(Long studentId);

    /**
     * Retrieves a list of grades by student and subject.
     *
     * @param student the student object
     * @param subject the subject object
     * @return a list of grades associated with the given student and subject
     */
    List<Grade> findByStudentAndSubject(Student student, Subject subject);
}
