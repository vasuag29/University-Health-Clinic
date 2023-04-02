DROP DATABASE IF EXISTS university_health_clinic;
CREATE DATABASE university_health_clinic;

USE university_health_clinic;

CREATE TABLE specialization (
name VARCHAR(20) PRIMARY KEY,
description TEXT
);

CREATE TABLE doctor (
doctor_id VARCHAR(7) PRIMARY KEY,
first_name VARCHAR(15) NOT NULL,
last_name VARCHAR(15) NOT NULL,
email_id VARCHAR(20) NOT NULL,
qualification VARCHAR(5) NOT NULL,
phone_number INT NOT NULL,
specialization VARCHAR(20),
CONSTRAINT specialization_fk FOREIGN KEY (specialization) REFERENCES specialization(name)
);

CREATE TABLE student (
student_id VARCHAR(7) PRIMARY KEY,
first_name VARCHAR(15) NOT NULL,
last_name VARCHAR(15) NOT NULL,
email_id VARCHAR(20) NOT NULL,
phone_number INT NOT NULL,
date_of_birth DATE NOT NULL,
sex ENUM("Male", "Female") NOT NULL,
degree_enrolled VARCHAR(30) NOT NULL,
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
quantity INT,
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


