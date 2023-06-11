package com.StudentsBase.StudentsBase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long>
{
    Optional<Grade> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findByStudentAndSubject(Student student, Subject subject);
}
