package com.StudentsBase.StudentsBase;

import jakarta.persistence.*;

import java.util.List;

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

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
