package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import org.apache.catalina.Manager;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {
        return countryService.readAll();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {
        return departmentService.readAll();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {
        return jobService.readAll();
    }

    // Display all the locations
    public static List<Location> getAllLocations() {
        return locationService.readAll();
    }

    // Display all the regions
    public static List<Region> getAllRegions() {
        return regionService.readAll();
    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {
        return jobHistoryService.readAll();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
        return getAllEmployees().stream()
                .map(Employee::getFirstName)
                .collect(toList());
    }

    // Display all the countries' names
    public static List<String> getAllCountryNames() {
        return getAllCountries().stream()
                .map(Country::getCountryName)
                .collect(toList());
    }

    // Display all the departments managers first names
    public static List<String> getAllDepartmentManagerFirstNames() {
//        return getAllDepartments().stream()
//                .map(Department::getManager)
//                .map(Employee::getFirstName)
//                .collect(toList());

        return getAllDepartments().stream()
                .map(department -> department.getManager().getFirstName())
                .collect(toList());
    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        return getAllDepartments().stream()
                .filter(manager -> manager.getManager().getFirstName().equals("Steven"))
                .collect(toList());
    }

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {
        return getAllDepartments().stream()
                .filter(m -> m.getLocation().getPostalCode().equals("98199"))
                .collect(toList());
    }

    // Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {
//        return getAllDepartments().stream()
//                .filter(department -> department.getDepartmentName().equals("IT"))
//                .map(d -> d.getLocation().getCountry().getRegion())
//                .findFirst().orElse(null);

        return getAllDepartments().stream()
                .filter(department -> department.getDepartmentName().equals("IT"))
                .findFirst().orElseThrow(() -> new Exception("Department is not found!"))
                .getLocation().getCountry().getRegion();
    }

    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        return getAllDepartments().stream()
                .filter(d -> d.getLocation().getCountry().getRegion().getRegionName().equals("Europe"))
                .collect(toList());
    }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {
        return getAllEmployees().stream()
                .allMatch(d -> d.getSalary() > 1000);
//        return employeeService.readAll().stream()
//                .noneMatch(employee -> employee.getSalary() < 1000);
//
//        return getAllEmployees().stream()
//                .filter(employee -> employee.getSalary()<1000)
//                .findAny().isEmpty();
    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        return getAllEmployees().stream()
                .filter(dep -> dep.getDepartment().getDepartmentName().equals("IT"))
                .allMatch(d -> d.getSalary() > 2000);
    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary() < 5000)
                .collect(toList());
    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary() > 6000 && employee.getSalary() < 7000)
                .collect(toList());
    }

    // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {
        return getAllEmployees().stream()
                .filter(e -> e.getFirstName().equals("Douglas") && e.getLastName().equals("Grant"))
                .findFirst().orElseThrow(() -> new Exception("Employee is not found!"))
                .getSalary();
    }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {
//        return getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .reduce(Long::max)
//                .orElseThrow();
//        return getAllEmployees().stream()
//                .max(Comparator.comparing(Employee::getSalary))
//                .get().getSalary();
//        return getAllEmployees().stream()
//                .sorted(Comparator.comparing(Employee::getSalary).reversed)
//                .findFirst().get().getSalary();
        return getAllEmployees().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(1).toList().get(0).getSalary();
    }

    // Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {
        return getAllEmployees().stream()
                .filter(d -> {
                    try {
                        return d.getSalary().equals(getMaxSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(toList());
    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {
        return getAllJobs().stream()
                .max(Comparator.comparing(Job::getMaxSalary))
                .orElseThrow();
    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
        return getAllEmployees().stream()
                .filter(d -> d.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
                .map(Employee::getSalary)
                .reduce(Long::max)
                .orElseThrow();
    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {
        return getAllEmployees().stream()
                .map(Employee::getSalary)
                .skip(1)
                .reduce(Long::max)
                .orElseThrow();
    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {
        return getAllEmployees().stream()
                .filter(employee -> {
                    try {
                        return employee.getSalary().equals(getSecondMaxSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(toList());
    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        return getAllEmployees().stream()
                .map(Employee::getSalary)
                .reduce(Long::min)
                .orElseThrow();
    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {
        return getAllEmployees().stream()
                .filter(employee -> {
                    try {
                        return employee.getSalary().equals(getMinSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(toList());
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception {
        return getAllEmployees().stream()
                .map(Employee::getSalary)
                .sorted()
                .skip(1)
                .reduce(Long::min)
                .orElseThrow();
    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() {
        return getAllEmployees().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .skip(1)
                .limit(2)
                .collect(toList());
    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {
        return getAllEmployees().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary() > getAverageSalary())
                .collect(toList());
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary() < getAverageSalary())
                .collect(toList());
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
        return getAllEmployees().stream()
                .collect(Collectors.groupingBy(employee -> employee.getDepartment().getId()));
    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        return (long) getAllDepartments().stream()
                .map(Department::getId)
                .toList().size();
    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        return getAllEmployees().stream()
                .filter(e -> e.getFirstName().equals("Alyssa")
                        && e.getManager().getFirstName().equals("Eleni")
                        && e.getDepartment().getDepartmentName().equals("Sales"))
                .findAny().orElseThrow();
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        return getAllJobHistories().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate))
                .collect(toList());
    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {
        return getAllJobHistories().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate).reversed())
                .collect(toList());
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate d1 = LocalDate.parse("01.01.2005", df);
        return jobHistoryService.readAll().stream()
                .filter(e -> e.getStartDate().isAfter(d1))
                .collect(toList());
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate d1 = LocalDate.parse("31.12.2007", df);
        return jobHistoryService.readAll().stream()
                .filter(e -> e.getEndDate().equals(d1) && e.getJob().getJobTitle().equals("Programmer"))
                .collect(toList());
    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate d1 = LocalDate.parse("01.01.2007", df);
        LocalDate d2 = LocalDate.parse("31.12.2007", df);
        return jobHistoryService.readAll().stream()
                .filter(e -> e.getStartDate().equals(d1)
                        && e.getEndDate().equals(d2)
                        && e.getDepartment().getDepartmentName().equals("Shipping"))
                .findFirst().orElseThrow().getEmployee();
    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getFirstName().startsWith("A"))
                .collect(toList());
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getJob().getId().contains("IT"))
                .collect(toList());
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        return getAllEmployees().stream()
                .filter(e -> e.getDepartment().getDepartmentName().equals("IT")
                        && e.getJob().getJobTitle().equals("Programmer"))
                .count();
    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        return getAllEmployees().stream()
                .filter(e -> e.getDepartment().getId().equals(80L)
                        || e.getDepartment().getId().equals(50L)
                        || e.getDepartment().getId().equals(100L))
                .collect(toList());
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        return getAllEmployees().stream()
                .map(e -> e.getFirstName().charAt(0) + String.valueOf(e.getLastName().charAt(0)))
                .collect(toList());
    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        return getAllEmployees().stream()
                .map(e -> e.getFirstName() + " " + e.getLastName())
                .collect(toList());
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() throws Exception {
        return getAllEmployeesFullNames().stream()
                .map(String::length)
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
        return employeeService.readAll().stream()
                .filter(employee -> {
                    try {
                        return (employee.getFirstName()+" "+employee.getLastName()).length()==getLongestNameLength();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(toList());
    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        return getAllEmployees().stream()
                .filter(e -> e.getDepartment().getId().equals(90L)
                        || e.getDepartment().getId().equals(60L)
                        || e.getDepartment().getId().equals(100L)
                        || e.getDepartment().getId().equals(120L)
                        || e.getDepartment().getId().equals(130L))
                .collect(toList());
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        return getAllEmployees().stream()
                .filter(e -> !e.getDepartment().getId().equals(90L)
                        && !e.getDepartment().getId().equals(60L)
                        && !e.getDepartment().getId().equals(100L)
                        && !e.getDepartment().getId().equals(120L)
                        && !e.getDepartment().getId().equals(130L))
                .collect(toList());
    }

}
