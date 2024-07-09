package entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDateTime;



@Data
public class stuClass implements Serializable {


    private Long id;
    private Long stuId;
    private String stuName;
    private String stuScore;
    private String stuStudyTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDate;

    //班级表
    private Long classId;
    private String className;
    private String teaId;
    private String classStudentNum;//班级学生人数
    private String deleted; //是否删除
    private String truename;


}
