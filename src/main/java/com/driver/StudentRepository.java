package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class StudentRepository {
    HashMap<String,Student> studentDb=new HashMap<>();
    HashMap<String,Teacher> teacherDb=new HashMap<>();
    HashMap<Teacher, List<Student>> teacherStudentPairDb=new HashMap<>();

    public void addStudent(Student student) {
        studentDb.put(student.getName(),student);
    }

    public void addTeacher(Teacher teacher) {
        teacherDb.put(teacher.getName(),teacher);
    }


    public void addStudentTeacherPair(String student, String teacher) {
        Teacher teacher1=getTeacherByName(teacher);
        teacher1.setNumberOfStudents(teacher1.getNumberOfStudents()+1);

        Student student1=getStudentByName(student);

        if(!teacherStudentPairDb.containsKey(teacher1)){
            teacherStudentPairDb.put(teacher1,new ArrayList<>());
        }
        teacherStudentPairDb.get(teacher1).add(student1);
    }

    public Student getStudentByName(String name) {
        Student student=studentDb.getOrDefault(name,null);
        return student==null?new Student():student;
    }

    public Teacher getTeacherByName(String name) {
        Teacher teacher=teacherDb.getOrDefault(name,null);
        return teacher==null?new Teacher():teacher;
    }

    public List<String> getStudentsByTeacherName(String teacher) {
        List<String> students=new ArrayList<>();
        Teacher teacher1=getTeacherByName(teacher);

        List<Student> list =teacherStudentPairDb.getOrDefault(teacher1,new ArrayList<>());
        for(Student student:list){
            students.add(student.getName());
        }
        return students;
    }

    public List<String> getAllStudents() {
        return new ArrayList<>(studentDb.keySet());
    }

    public void deleteTeacherByName(String teacher) {
        Teacher teacher1=getTeacherByName(teacher);
        List<Student> students=teacherStudentPairDb.getOrDefault(teacher1,new ArrayList<>());
        for(Student student:students){
            studentDb.remove(student.getName());
        }
        teacherDb.remove(teacher);
        teacherStudentPairDb.remove(teacher1);
    }

    public void deleteAllTeachers() {
        for(String teacher:teacherDb.keySet()){
            deleteTeacherByName(teacher);
        }
        teacherStudentPairDb.clear();
    }
}