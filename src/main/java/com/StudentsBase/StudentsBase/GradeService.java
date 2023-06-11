package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GradeService
{
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

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
            Optional<Grade> existingGrade = gradeRepository.findByStudentIdAndSubjectId(studentId, subjectId);
            existingGrade.get().setMark(grade.getMark());

            return gradeRepository.save(existingGrade.get());
        }
    }
}
