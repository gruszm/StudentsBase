package com.StudentsBase.StudentsBase;

public class GradeDTO
{
    private Long gradeId;
    private Long studentId;
    private Long subjectId;
    private Integer mark;

    public GradeDTO(Grade grade)
    {
        this.gradeId = grade.getId();
        this.studentId = grade.getStudent().getId();
        this.subjectId = grade.getSubject().getId();
        this.mark = grade.getMark();
    }

    public Long getGradeId()
    {
        return gradeId;
    }

    public Long getStudentId()
    {
        return studentId;
    }

    public Long getSubjectId()
    {
        return subjectId;
    }

    public Integer getMark()
    {
        return mark;
    }
}
