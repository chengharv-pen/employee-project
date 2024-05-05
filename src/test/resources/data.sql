DROP TABLE IF EXISTS employee_table;

CREATE TABLE employee_table(
	emp_id INT AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR(50) DEFAULT NULL,
	last_name VARCHAR(50) DEFAULT NULL,
	email VARCHAR(50) DEFAULT NULL
);

INSERT INTO employee_table(first_name, last_name, email) VALUES('Daniel', 'Pen', 'daniel.pen@gmail.com');
INSERT INTO employee_table(first_name, last_name, email) VALUES('Daniel', 'Pen', 'daniel.pen@gmail.com');
INSERT INTO employee_table(first_name, last_name, email) VALUES('Daniel', 'Pen', 'daniel.pen@gmail.com');