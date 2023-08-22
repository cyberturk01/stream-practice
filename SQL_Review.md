select *
from students;

select *
from employees;

select *
from regions;

select employee_id, first_name, department
from employees;

select *
from employees
where department = 'Sports';

select *
from employees
where department like 'G%';

select first_name, last_name as fullname, department, salary
from employees
where department = 'Clothing'
and salary > 90000;


select first_name, last_name as fullname, department, salary
from employees
where salary < 40000
and (department = 'Clothing' or department = 'Pharmacy');
--Same results
select first_name, last_name as fullname, department, salary
from employees
where salary < 40000
and department IN ('Clothing', 'Pharmacy');

--Not Symbols != or <>
select first_name as fullname, department, salary
from employees
where department != 'Sports';

select first_name as fullname, department, salary
from employees
where department <> 'Tools';

select first_name, email
from employees
where email is null;

select *
from employees
where salary between 80000 and 100000;


select first_name, email
from employees
where gender = 'F'
And department = 'Tools'
and salary > 110000
order by first_name;

--sort employees based on
select *
from employees
order by first_name DESC;

select first_name, email
from employees
where gender = 'M'
and department = 'Tools'
and salary > 60000
limit 10;

--limit in postresql
select first_name, email
from employees
where salary > 60000
FETCH FIRST 5 ROWS ONLY;

--Unique departments
select Distinct department
from employees;

--display name and age of 4 oldest students
select student_name, age
from students
order by age DESC
limit 4;

select upper(first_name), lower(last_name)
from employees;

select length(first_name)
from employees;

select concat(first_name, ' ', last_name) as full_name
from employees;

select first_name || ' ' || last_name as full_name
from employees;

select first_name, salary, (salary > 140000) as result
from employees
order by salary DESC;


select substring(first_name, 3), email
from employees
where gender = 'M'
and department = 'Tools'
and salary > 60000
limit 10;

select substring(first_name, 0, 2) || '.' || substring(last_name, 0, 2) as initials, first_name, last_name
from employees;

select min(salary)
from employees;

select max(salary)
from employees;

--Round result
select round(avg(salary), 2)
from employees;

select count(email)
from employees;

select sum(salary)
from employees
where department = 'Clothing';

select *
from employees
where salary in (select min(salary)
from employees)
or salary in (select max(salary)
from employees)
order by salary;

select Substring(email, (position('@' in email) + 1))
from employees
where email Is not null;

--how many workers working in pharmacy department
select count(*)
from employees
where department = 'Pharmacy';


--how many workers work for each department
select department as allDep, count(*)
from employees
group by department
order by department ASC;

--how many workers work for each department and min, max, avg and sum of the salaries
select department as deps, count(*), max(salary), min(salary), round(avg(salary)), sum(salary)
from employees
group by deps
order by deps;


--list the departments where they have more than 40 employee
select department, count(*)
from employees
where department <> 'Garden'
group by department
having count(*) > 40
order by department;
--We use WHERE to filter before grouping
--We use HAVING to filter after grouping

--duplicate emails
select count(*), Substring(email, (position('@' in email) + 1)) as domain
from employees
where email Is not null
group by domain
having count(*) > 1
order by count(*) DESC;

Select first_name,
salary,
CASE
when salary < 80000 Then 'Under Paid'
when salary > 80000 Then 'Paid Well'
Else 'Unpaid'
End as category
from employees;


