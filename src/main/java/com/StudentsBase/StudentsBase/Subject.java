package com.StudentsBase.StudentsBase;

import jakarta.persistence.*;

import java.util.List;

/**
 * Entity class for representing a Subject in the StudentsBase application.
 *
 * @author Mariusz Gruszczynski, Mateusz Pysera
 * @version 1.0
 * @since JDK 17
 */
@Entity
@Table
public class Subject
{
    @Id
    @SequenceGenerator(name = "subject_sequence",
            sequenceName = "subject_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "subject_sequence"
    )
    private Long id;
    private String name;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.REMOVE)
    private List<Grade> grades;

    /**
     * Gets the ID of the subject.
     *
     * @return the ID of the subject
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Sets the ID of the subject.
     *
     * @param id the new ID of the subject
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * Gets the name of the subject.
     *
     * @return the name of the subject
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the subject.
     *
     * @param name the new name of the subject
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the grades of the subject.
     *
     * @return the grades of the subject
     */
    public List<Grade> getGrades()
    {
        return grades;
    }
}
