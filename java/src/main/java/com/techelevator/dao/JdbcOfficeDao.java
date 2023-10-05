package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Employee;
import com.techelevator.model.Office;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcOfficeDao implements OfficeDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcOfficeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Office> getAllOfficeDetails() {
        List<Office> offices = new ArrayList<>();
        String sql = "SELECT office_id, office_name, phone_number, open_time, close_time, address, city, state, zip, service_fee\n" +
                "\tFROM office_details;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                offices.add(mapRowToOffice(results));
            }
        } catch (CannotGetJdbcConnectionException ex){
            throw new DaoException("Cannot connect to server or database", ex);
        }
        return offices;
    }

    @Override
    public Office getOfficeById(int officeId) {
        String sql = "SELECT office_id, office_name, phone_number, open_time, close_time, address, city, state, zip, service_fee\n" +
                "\tFROM office_details WHERE office_id = ? ";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, officeId);
        if (rowSet.next()) {
            Office office = mapRowToOffice(rowSet);
            office.setEmployees(getEmployeesForOfficeId(office.getOfficeId()));
            return office;
        } else {
            return null;
        }
    }

    public List<Employee> getEmployeesForOfficeId(int officeId){
        List<Employee> result = new ArrayList<>();
        String sql="SELECT employee.employee_id, employee.first_name, employee.last_name\n" +
                "FROM employee\n" +
                "JOIN employee_office\n" +
                "ON employee_office.employee_id = employee.employee_id\n" +
                "JOIN office_details\n" +
                "ON employee_office.office_id = office_details.office_id\n" +
                "WHERE office_details.office_id = ?; ";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, officeId);
        while(rowSet.next()){
            Employee employee = mapRowToEmployee(rowSet);
            result.add(employee);
        }
        return result;
    }


    private Office mapRowToOffice(SqlRowSet rowSet) {
        Office office = new Office(
                rowSet.getInt("office_id"),
                rowSet.getString("office_name"),
                rowSet.getString("phone_number"),
                rowSet.getTime("open_time"),
                rowSet.getTime("close_time"),
                rowSet.getString("address"),
                rowSet.getString("city"),
                rowSet.getString("state"),
                rowSet.getString("zip"),
                rowSet.getBigDecimal("service_fee")
        );
          return office;
    }

    private Employee mapRowToEmployee(SqlRowSet rowSet){
        Employee employee = new Employee(
        rowSet.getInt("employee_id"),
        rowSet.getString("first_name"),
        rowSet.getString("last_name")
        );
        return employee;
    }
}
