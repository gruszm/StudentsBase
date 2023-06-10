package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, GradeRepository gradeRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student updateStudent(Long id, Student newStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(newStudent.getFirstName());
                    student.setLastName(newStudent.getLastName());
                    student.setIndexNumber(newStudent.getIndexNumber());
                    return studentRepository.save(student);
                })
                .orElseGet(() -> {
                    newStudent.setId(id);
                    return studentRepository.save(newStudent);
                });
    }

    public Student addSubjectToStudent(Long studentId, Long subjectId)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student with id " + studentId + " not found"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new RuntimeException(("Subject with id " + subjectId + " not found")));
        boolean subjectAlreadyAssigned = student.getSubjects().contains(subject);

        if (!subjectAlreadyAssigned)
        {
            student.getSubjects().add(subject);
        }
        else
        {
            throw new RuntimeException(String.format("Subject %d already assigned to student %d", subjectId, studentId));
        }

        studentRepository.save(student);

        return student;
    }

    public List<Subject> getSubjectsAssignedToStudent(Long studentId)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student with id " + studentId + " not found"));
        return student.getSubjects();
    }

    public List<Grade> getGrades(Long studentId)
    {
        List<Grade> allGrades = gradeRepository.findAll();
        List<Grade> filteredGrades = new ArrayList<>();

        for (Grade g : allGrades)
        {
            if (g.getStudent().getId() == studentId)
            {
                filteredGrades.add(g);
            }
        }

        return filteredGrades;
    }
}
