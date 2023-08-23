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

--STRING MANIPULATION

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

--Count total emails

    select count(email)
    from employees;

--Get total salary in Colothing department

    select sum(salary)
    from employees
    where department = 'Clothing';

--select min and max salary

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

**--Creating Functions plpgsql with parameter**

    create or replace function get_department_count_by_name(dep_name varchar)
        returns int
        language plpgsql
    as
    
    $$
    DECLARE
        department_count integer;
    begin
        select count(*)
        into department_count
        from employees
        where department = dep_name;

        return department_count;
    end;
    $$;

--CREATING FUNCTION FOR THE TABLE
    
    create or replace function get_department(p_pattern varchar)
        returns table(
                    employee_name varchar,
                    employee_email varchar
                )
        language plpgsql
    as
    $$
    begin
        return query
        select first_name,email
        from employees
        where department ilike p_pattern;
    end;
    $$;

*-->Create function and run sql query from postgresql*

    select get_department_count_by_name('Sports');

--INDEXING
--CREATE TABLE TOWNS

    CREATE TABLE Towns (
    id SERIAL UNIQUE NOT NULL,
    code VARCHAR(10) NOT NULL, -- not unique
    article TEXT,
    name TEXT NOT NULL -- not unique
    );
    
--INSERT DATA TO TOWNS

    insert into towns (
    code, article, name
    )
    select
    left(md5(i::text), 10),
    md5(random()::text),
    md5(random()::text)
    from generate_series(1, 1000000) s(i);

--CHECK TOWNS

    select count(*)
    FROM towns;
    
    select *
    from towns;
    
--CHECK THE PERFORMANCE OF THE QUERY

    EXPLAIN ANALYZE
    SELECT *
    FROM towns
    WHERE name = 'ba17178f22f6522d5a3c3df87f6f2c43';
    
    EXPLAIN ANALYZE
    SELECT *
    FROM towns
    WHERE id = 1232;
    
--CREATE INDEX TO MAKE BETTER PERFORMANCE

    CREATE INDEX idx_towns_name ON towns(name);
    CREATE INDEX idx_towns_article ON towns(article);

--DROP THE INDEX

    DROP INDEX IF EXISTS idx_towns_name;

--CHECK INDEXES CREATED
    
    SELECT
        tablename,
        indexname,
        indexdef
    FROM
        pg_indexes
    WHERE
        schemaname = 'public'
    ORDER BY
        tablename,
        indexname;

--REMOVE INDEX 

    DROP FUNCTION get_department_count_by_name(dep_name varchar);


--CREATE PROCEDURE
    
    create or replace procedure update_department(emp_id int)
        language plpgsql
    as
    $$
    begin
        update employees set department ='Toys'
        where employee_id = emp_id;
        commit;
    end;
    $$;

    call update_department(10)

--Create TRIGGER 

    DROP TABLE IF EXISTS mentors;
    CREATE TABLE mentors(
        id INT GENERATED ALWAYS AS IDENTITY,
        first_name varchar(40) not null,
        last_name varchar(40) not null ,
        primary key(id)
    );
    
    DROP TABLE IF EXISTS mentor_audit;
    CREATE TABLE mentor_audit(
        id INT GENERATED ALWAYS AS IDENTITY,
        mentor_id INT not null,
        last_name varchar(40) not null ,
        changed_on timestamp(6) not null,
        primary key(id)
    );

    insert into mentors(first_name, last_name) values ('Harold','Finch');
    insert into mentors(first_name, last_name) values ('Severus','Snape');

    CREATE OR REPLACE FUNCTION log_last_name_changes()
        returns trigger
        language plpgsql
        as
        $$
        begin
            IF NEW.last_name <> OLD.last_name THEN
            INSERT INTO mentor_audit(mentor_id,last_name,changed_on) VALUES
            (OLD.id,OLD.last_name,now());
        end if;

        Return NEW;
    end
    $$;

    CREATE TRIGGER last_name_change
        BEFORE update
        ON mentors
        FOR EACH ROW
        EXECUTE PROCEDURE log_last_name_changes();
    
    update mentors
    set last_name ='ABC'
    where id =2;