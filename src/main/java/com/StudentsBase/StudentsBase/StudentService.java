package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The StudentService class provides operations for managing students in the database.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
@Service
public class StudentService
{
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;

    /**
     * Constructor for the StudentService class.
     *
     * @param studentRepository repository for student-related database operations
     * @param gradeRepository repository for grade-related database operations
     * @param subjectRepository repository for subject-related database operations
     */
    @Autowired
    public StudentService(StudentRepository studentRepository, GradeRepository gradeRepository, SubjectRepository subjectRepository)
    {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.subjectRepository = subjectRepository;
    }

    /**
     * Gets all students from the database.
     *
     * @return list of all students
     */
    public List<Student> getStudents()
    {
        return studentRepository.findAll();
    }

    /**
     * Gets a student by its ID.
     *
     * @param id the ID of the student
     * @return the student with the given ID
     * @throws RuntimeException if the student is not found
     */
    public Student getStudent(Long id)
    {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    /**
     * Adds a new student to the database.
     *
     * @param student the student to be added
     * @return the added student
     * @throws RuntimeException if the student already exists
     * @throws IllegalArgumentException if the index number does not contain only digits
     */
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

    /**
     * Deletes a student from the database by its ID.
     *
     * @param id the ID of the student to be deleted
     */
    public void deleteStudent(Long id)
    {
        studentRepository.deleteById(id);
    }

    /**
     * Updates a student in the database.
     *
     * @param id the ID of the student to be updated
     * @param newStudent the new student data
     * @return the updated student
     */
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

    /**
     * Assigns a subject to a student.
     *
     * @param studentId the ID of the student
     * @param subjectId the ID of the subject
     * @return the updated student
     * @throws RuntimeException if the student or the subject is not found or the subject is already assigned
     */
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

    /**
     * Gets the subjects assigned to a student.
     *
     * @param studentId the ID of the student
     * @return list of the assigned subjects
     * @throws RuntimeException if the student is not found
     */
    public List<Subject> getSubjectsAssignedToStudent(Long studentId)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student with id " + studentId + " not found"));
        return student.getSubjects();
    }

    /**
     * Removes a subject from a student.
     *
     * @param studentId the ID of the student
     * @param subjectId the ID of the subject
     * @throws RuntimeException if the student or the subject is not found or the subject is not assigned to the student
     */
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

    /**
     * Gets the grades of a student.
     *
     * @param studentId the ID of the student
     * @return list of the student's grades
     */
    public List<Grade> getGrades(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    /**
     * Gets the grade of a student for a subject.
     *
     * @param studentId the ID of the student
     * @param subjectId the ID of the subject
     * @return the grade
     * @throws RuntimeException if the grade is not found
     */
    public Grade getGrade(Long studentId, Long subjectId)
    {
        return gradeRepository.findByStudentIdAndSubjectId(studentId, subjectId).orElseThrow(() -> new RuntimeException("Grade not found"));
    }
}
