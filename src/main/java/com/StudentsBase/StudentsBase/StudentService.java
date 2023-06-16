package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService
{
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, GradeRepository gradeRepository, SubjectRepository subjectRepository)
    {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Student> getStudents()
    {
        return studentRepository.findAll();
    }

    public Student getStudent(Long id)
    {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student addStudent(Student student)
    {
        if (studentRepository.findByIndexNumber(student.getIndexNumber()).isPresent())
        {
            throw new RuntimeException("Student with index number " + student.getIndexNumber() + " already exists");
        }

        if (!student.getIndexNumber().matches("\\d+")) {
            throw new IllegalArgumentException("Index number must contain only digits");
        }

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id)
    {
        studentRepository.deleteById(id);
    }

    public Student updateStudent(Long id, Student newStudent)
    {
        return studentRepository.findById(id)
                .map(student ->
                {
                    student.setFirstName(newStudent.getFirstName());
                    student.setLastName(newStudent.getLastName());
                    student.setIndexNumber(newStudent.getIndexNumber());
                    return studentRepository.save(student);
                })
                .orElseGet(() ->
                {
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

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setSubject(subject);

        gradeRepository.save(grade);

        return student;
    }

    public List<Subject> getSubjectsAssignedToStudent(Long studentId)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student with id " + studentId + " not found"));
        return student.getSubjects();
    }

    public void removeSubjectFromStudent(Long studentId, Long subjectId)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student with id " + studentId + " not found"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new RuntimeException("Subject with id " + subjectId + " not found"));

        if (student.getSubjects().contains(subject))
        {
            List<Grade> gradesToRemove = gradeRepository.findByStudentAndSubject(student, subject);
            gradeRepository.deleteAll(gradesToRemove);

            student.getSubjects().remove(subject);
            studentRepository.save(student);
        }
        else
        {
            throw new RuntimeException("Student with id " + studentId + " does not have subject with id " + subjectId);
        }
    }

    public List<Grade> getGrades(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public Grade getGrade(Long studentId, Long subjectId)
    {
        return gradeRepository.findByStudentIdAndSubjectId(studentId, subjectId).orElseThrow(() -> new RuntimeException("Grade not found"));
    }
}
