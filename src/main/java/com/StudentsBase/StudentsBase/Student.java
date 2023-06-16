package com.StudentsBase.StudentsBase;

import jakarta.persistence.*;

import java.util.List;

/**
 * The Student class represents a student entity in the database.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
@Entity
@Table
public class Student
{
    @Id
    @SequenceGenerator(name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String indexNumber;

    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Grade> grades;

    /**
     * Gets the list of subjects associated with the student.
     *
     * @return the list of student's subjects
     */
    public List<Subject> getSubjects()
    {
        return subjects;
    }

    /**
     * Sets the list of subjects for the student.
     *
     * @param subjects the list of subjects to be set for the student
     */
    public void setSubjects(List<Subject> subjects)
    {
        this.subjects = subjects;
    }

    /**
     * Gets the student's ID.
     *
     * @return the student's ID
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Sets the student's ID.
     *
     * @param id the ID to be set
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * Gets the student's first name.
     *
     * @return the student's first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets the student's first name.
     *
     * @param firstName the first name to be set
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Gets the student's last name.
     *
     * @return the student's last name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets the student's last name.
     *
     * @param lastName the last name to be set
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Gets the student's index number.
     *
     * @return the student's index number
     */
    public String getIndexNumber()
    {
        return indexNumber;
    }

    /**
     * Sets the student's index number.
     *
     * @param indexNumber the index number to be set
     */
    public void setIndexNumber(String indexNumber)
    {
        this.indexNumber = indexNumber;
    }
}
