package com.yourname.dao;
import com.yourname.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository("mysql")
public class MySqlStudentDaoImpl implements StudentDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {

            Student student = new Student();
            student.setId(resultSet.getInt("id"));
            student.setName(resultSet.getString("name"));
            student.setCourse(resultSet.getString("course"));
            return student;
        }
    }
    @Override
    public Collection<Student> getAllStudents() {
        //   SELECT column_name(s) FROM table_name

        final String sql = "SELECT id, name, course FROM students1";
        List<Student> students1 = jdbcTemplate.query(sql, new StudentRowMapper());

        return students1;
    }

    @Override
    public Student getStudentById(int id) {
        final String sql = "SELECT id, name, course FROM students1 where id = ?";
        Student student = jdbcTemplate.queryForObject(sql, new StudentRowMapper(), id);
        return student;
    }

    @Override
    public void removeStudentById(int id) {
        final String sql = "DELETE FROM students1 WHERE id=?";
        jdbcTemplate.update(sql,id);

    }

    @Override
    public void updateStudent(Student student) {
        final String sql = "UPDATE students1 SET name = ?, course=? WHERE id=?";
        int id = student.getId();
        String name = student.getName();
        String course = student.getCourse();
        jdbcTemplate.update(sql,new Object[]{name, course, id});

    }

    @Override
    public void insertStudentToDb(Student student) {
        final String sql = "INSERT INTO students1 (name, course) VALUES (?,?)";
        //final int id = student.getId();
        final String name = student.getName();
        final String course = student.getCourse();
        jdbcTemplate.update(sql, new Object[]{name,course});


    }
}
