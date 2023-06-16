package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service for handling operations related to grades in the StudentsBase system.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
@Service
public class GradeService
{
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final List<Double> allowedMarks = Arrays.asList(2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0);

    /**
     * Creates a new GradeService with the given repositories.
     *
     * @param gradeRepository    the grade repository
     * @param studentRepository  the student repository
     * @param subjectRepository  the subject repository
     */
    @Autowired
    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, SubjectRepository subjectRepository)
    {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    /**
     * Returns a list of all grades in the system.
     *
     * @return a list of all grades
     */
    public List<Grade> getGrades()
    {
        return gradeRepository.findAll();
    }

    /**
     * Adds a new grade to the system.
     *
     * @param grade the grade to add
     * @return the added grade
     * @throws IllegalArgumentException if the grade's mark is not one of the allowed values
     * @throws RuntimeException if the student and subject are not associated or not found
     */
    public Grade addGrade(Grade grade)
    {
        if ((!allowedMarks.contains(grade.getMark())) && (grade.getMark() != null))
        {
            throw new IllegalArgumentException("Invalid mark. The mark must be one of the following: " + allowedMarks.toString());
        }

        Long studentId = grade.getStudent().getId();
        Long subjectId = grade.getSubject().getId();

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student with id " + studentId + " not found"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new RuntimeException(String.format("Subject with id %d not found", subjectId)));

        if ((student.getSubjects() == null) || (!student.getSubjects().contains(subject)))
        {
            throw new RuntimeException("Subject is not assigned to this student");
        }
        else
        {
            Grade existingGrade = gradeRepository.findByStudentIdAndSubjectId(studentId, subjectId).orElseThrow();
            existingGrade.setMark(grade.getMark());

            return gradeRepository.save(existingGrade);
        }
    }
}
