DROP DATABASE IF EXISTS university_health_clinic;
CREATE DATABASE university_health_clinic;

USE university_health_clinic;

CREATE TABLE specialization (
name VARCHAR(40) PRIMARY KEY,
description TEXT
);

CREATE TABLE qualification (
abbreviation VARCHAR(10) PRIMARY KEY,
name VARCHAR(40) UNIQUE NOT NULL
);

CREATE TABLE doctor (
doctor_id VARCHAR(7) PRIMARY KEY,
first_name VARCHAR(15) NOT NULL,
last_name VARCHAR(15) NOT NULL,
email_id VARCHAR(40) UNIQUE NOT NULL,
qualification VARCHAR(10) NOT NULL,
phone_number VARCHAR(10) UNIQUE NOT NULL,
specialization VARCHAR(40),
CONSTRAINT specialization_fk FOREIGN KEY (specialization) REFERENCES specialization(name),
CONSTRAINT qualification_fk FOREIGN KEY (qualification) REFERENCES qualification(abbreviation)
);

CREATE TABLE student (
student_id VARCHAR(7) PRIMARY KEY,
first_name VARCHAR(15) NOT NULL,
last_name VARCHAR(15) NOT NULL,
email_id VARCHAR(40) UNIQUE NOT NULL,
phone_number VARCHAR(10) UNIQUE NOT NULL,
date_of_birth DATE NOT NULL,
sex ENUM("Male", "Female") NOT NULL,
degree_enrolled VARCHAR(60) NOT NULL,
university_health_insurance BOOL
);

CREATE TABLE appointment (
appointment_id VARCHAR(5) PRIMARY KEY,
appointment_time TIME,
appointment_date DATE
);

CREATE TABLE appointment_booking (
appointment_id VARCHAR(5),
doctor_id VARCHAR(7),
student_id VARCHAR(7),
FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id),
FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id),
FOREIGN KEY (student_id) REFERENCES student(student_id)
);

CREATE TABLE lab_report (
report_id VARCHAR(7) PRIMARY KEY,
report_description TEXT
);

CREATE TABLE diagnosis (
report_id VARCHAR(7),
doctor_id VARCHAR(7),
student_id VARCHAR(7),
diagnosis_description TEXT,
FOREIGN KEY (report_id) REFERENCES lab_report(report_id),
FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id),
FOREIGN KEY (student_id) REFERENCES student(student_id)
);

CREATE TABLE medicine (
name VARCHAR(20),
manufacturer VARCHAR(20),
PRIMARY KEY (name, manufacturer)
);

CREATE TABLE prescription (
doctor_id VARCHAR(7),
student_id VARCHAR(7),
medicine VARCHAR(20),
FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id),
FOREIGN KEY (student_id) REFERENCES student(student_id),
FOREIGN KEY (medicine) REFERENCES medicine(name)
);

# ------------------------------------- Procedures -------------------------------------

DROP PROCEDURE IF EXISTS student_by_id;
DELIMITER $$
CREATE PROCEDURE student_by_id(s_id VARCHAR(7))
BEGIN
	DECLARE email_id VARCHAR(40);
	
    SELECT email_id  INTO std_id FROM student WHERE student_id = s_id;
    
    IF email_id is NULL THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'The given student is not found in the records.';
    ELSE
		SELECT * FROM student
		WHERE student_id = s_id;
	END IF;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS get_appointments;
DELIMITER $$
CREATE PROCEDURE get_appointments()
BEGIN
	DECLARE num_appts VARCHAR(40);
	
    SELECT COUNT(*) INTO num_appts FROM appointment_booking;
    
    IF num_appts = 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'No appointments found';
    ELSE
		SELECT a.*, ab.student_id, ab.doctor_id FROM appointment_booking AS ab
		LEFT JOIN appointment AS a ON a.appointment_id = ab.appointment_id;
	END IF;
END $$
DELIMITER ;


