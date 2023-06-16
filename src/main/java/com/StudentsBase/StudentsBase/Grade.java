package com.StudentsBase.StudentsBase;

import jakarta.persistence.*;

/**
 * Entity representing a Grade in the StudentsBase system.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
@Entity
@Table
public class Grade
{
    @Id
    @SequenceGenerator(name = "grade_sequence",
            sequenceName = "grade_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "grade_sequence"
    )
    private Long id;
    private Double mark;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Subject subject;

    /**
     * Gets the grade's id.
     *
     * @return the grade's id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Sets the grade's id.
     *
     * @param id the grade's id
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * Gets the grade's mark.
     *
     * @return the grade's mark
     */
    public Double getMark()
    {
        return mark;
    }

    /**
     * Sets the grade's mark.
     *
     * @param mark the grade's mark
     */
    public void setMark(Double mark)
    {
        this.mark = mark;
    }

    /**
     * Gets the student associated with this grade.
     *
     * @return the student associated with this grade
     */
    public Student getStudent()
    {
        return student;
    }

    /**
     * Sets the student associated with this grade.
     *
     * @param student the student associated with this grade
     */
    public void setStudent(Student student)
    {
        this.student = student;
    }

    /**
     * Gets the subject associated with this grade.
     *
     * @return the subject associated with this grade
     */
    public Subject getSubject()
    {
        return subject;
    }

    /**
     * Sets the subject associated with this grade.
     *
     * @param subject the subject associated with this grade
     */
    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }
}
