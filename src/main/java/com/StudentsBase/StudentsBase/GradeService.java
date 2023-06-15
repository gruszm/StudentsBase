package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GradeService
{
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final List<Double> allowedMarks = Arrays.asList(2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0);

    @Autowired
    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, SubjectRepository subjectRepository)
    {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<GradeDTO> getGrades()
    {
        List<Grade> allGrades = gradeRepository.findAll();
        List<GradeDTO> gradeDTOs = new ArrayList<>();

        for (Grade grade : allGrades)
        {
            GradeDTO gradeDTO = new GradeDTO(grade);
            gradeDTOs.add(gradeDTO);
        }

        return gradeDTOs;
    }


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
