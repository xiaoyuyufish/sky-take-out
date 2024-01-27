package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.Autofill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Insert("insert into employee (sky_take_out.employee.name," +
            " sky_take_out.employee.username, sky_take_out.employee.password, sky_take_out.employee.phone, " +
            "sky_take_out.employee.sex, sky_take_out.employee.id_number, sky_take_out.employee.status, " +
            "sky_take_out.employee.create_time, sky_take_out.employee.update_time, " +
            "sky_take_out.employee.create_user, sky_take_out.employee.update_user) values"
            + "(#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}," +
            "#{updateTime}, #{createUser}, #{updateUser});")
    @Autofill(value = OperationType.INSERT)
    void insert(Employee employee);

    Page pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @Autofill(value = OperationType.UPDATE)
    void update(Employee employee);

    @Select("select * from sky_take_out.employee where id = #{id}")
    Employee getById(Long id);
}
