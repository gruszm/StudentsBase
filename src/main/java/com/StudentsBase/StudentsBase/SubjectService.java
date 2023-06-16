package com.StudentsBase.StudentsBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The SubjectService class provides operations for managing subjects in the database.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
@Service
public class SubjectService
{
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    /**
     * Constructor for the SubjectService class.
     *
     * @param subjectRepository repository for subject-related database operations
     * @param studentRepository repository for student-related database operations
     */
    @Autowired
    public SubjectService(SubjectRepository subjectRepository, StudentRepository studentRepository)
    {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Gets all subjects from the database.
     *
     * @return list of all subjects
     */
    public List<Subject> getSubjects()
    {
        return subjectRepository.findAll();
    }

    /**
     * Gets a subject by its ID.
     *
     * @param id the ID of the subject
     * @return the subject with the given ID
     * @throws RuntimeException if the subject is not found
     */
    public Subject getSubject(Long id)
    {
        return subjectRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Subject with id %d not found", id)));
    }

    /**
     * Adds a new subject to the database.
     *
     * @param subject the subject to be added
     * @return the added subject
     * @throws RuntimeException if the subject already exists
     */
    public Subject addSubject(Subject subject)
    {
        Subject existingSubject = subjectRepository.findByName(subject.getName());

        if (existingSubject != null)
        {
            throw new RuntimeException("Subject with name " + subject.getName() + " already exists");
        }
        return subjectRepository.save(subject);
    }

    /**
     * Deletes a subject from the database by its ID.
     *
     * @param id the ID of the subject to be deleted
     * @throws RuntimeException if the subject is not found
     */
    public void deleteSubject(Long id)
    {
        Subject thisSubject = subjectRepository.findById(id).orElseThrow(() -> new RuntimeException(("Subject with id " + id + " not found")));

        List<Student> studentsWithSubject = studentRepository.findBySubjectsContains(thisSubject);

        for (Student student : studentsWithSubject)
        {
            student.getSubjects().remove(thisSubject);
            studentRepository.save(student);
        }

        subjectRepository.deleteById(id);
    }

    /**
     * Updates a subject in the database.
     *
     * @param id the ID of the subject to be updated
     * @param newSubject the new subject data
     * @return the updated subject
     */
    public Subject updateSubject(Long id, Subject newSubject)
    {
        return subjectRepository.findById(id)
                .map(subject ->
                {
                    subject.setName(newSubject.getName());
                    return subjectRepository.save(subject);
                })
                .orElseGet(() ->
                {
                    newSubject.setId(id);
                    return subjectRepository.save(newSubject);
                });
    }
}
