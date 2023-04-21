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
appointment_id INT PRIMARY KEY AUTO_INCREMENT,
appointment_time TIME,
appointment_date DATE
);

CREATE TABLE appointment_booking (
appointment_id INT,
doctor_id VARCHAR(7),
student_id VARCHAR(7),
FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id),
FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id),
FOREIGN KEY (student_id) REFERENCES student(student_id)
);

CREATE TABLE lab_test (
test_name VARCHAR(100) PRIMARY KEY,
test_description TEXT
);

CREATE TABLE lab_test_for_appointment(
test_name VARCHAR(100),
appointment_id INT,
FOREIGN KEY (test_name) REFERENCES lab_test(test_name),
FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id)
);

CREATE TABLE lab_report (
report_id INT PRIMARY KEY AUTO_INCREMENT,
report_description TEXT
);

CREATE TABLE lab_reports_for_appointment (
report_id INT,
appointment_id INT,
FOREIGN KEY (report_id) REFERENCES lab_report(report_id),
FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id)
);

CREATE TABLE diagnosis (
report_id INT,
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

CALL student_by_id('7654321');
DROP PROCEDURE IF EXISTS student_by_id;
DELIMITER $$
CREATE PROCEDURE student_by_id(s_id VARCHAR(7))
BEGIN
	DECLARE std_id VARCHAR(40);
    SELECT email_id INTO std_id FROM student WHERE student_id = s_id;

    
    IF std_id is NULL THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'The given student is not found in the records.';
	ELSE
		SELECT * FROM student
		WHERE student_id = s_id;
	END IF;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS doctor_by_id;
DELIMITER $$
CREATE PROCEDURE doctor_by_id(d_id VARCHAR(7))
BEGIN
	DECLARE doc_id VARCHAR(40);
    SELECT email_id INTO doc_id FROM doctor WHERE doctor_id = d_id;
    
    IF doc_id is NULL THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'The given student is not found in the records.';
    ELSE
		SELECT * FROM doctor
		WHERE doctor_id = d_id;
	END IF;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS get_appointments;
DELIMITER $$
CREATE PROCEDURE get_appointments()
BEGIN
	DECLARE num_appts INT;
	
    SELECT COUNT(*) INTO num_appts FROM appointment_booking;
    
    IF num_appts = 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'No appointments found';
    ELSE
		SELECT a.*, ab.student_id, ab.doctor_id FROM appointment AS a
		LEFT JOIN appointment_booking AS ab ON a.appointment_id = ab.appointment_id;
	END IF;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS get_lab_reports;
DELIMITER $$
CREATE PROCEDURE get_lab_reports()
BEGIN
	DECLARE num_reports INT;
	
    SELECT COUNT(*) INTO num_reports FROM lab_report;
    IF num_reports = 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'No lab reports found';
    ELSE
		SELECT * FROM lab_report;
	END IF;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS get_appointments_by_student_id;
DELIMITER $$
CREATE PROCEDURE get_appointments_by_student_id(stu_id VARCHAR(7))
BEGIN
	DECLARE num_appts INT;
	
    SELECT COUNT(*) INTO num_appts FROM appointment_booking where student_id = stu_id;
    
    IF num_appts = 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'No appointments found';
    ELSE
		SELECT a.*, ab.student_id, ab.doctor_id FROM appointment_booking AS ab
		JOIN appointment AS a ON a.appointment_id = ab.appointment_id AND ab.student_id = stu_id;
	END IF;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS get_available_doctors;
DELIMITER $$
CREATE PROCEDURE get_available_doctors(
d_specialization VARCHAR(40), 
appt_date DATE, 
appt_time TIME)
BEGIN
	DECLARE num_available_doctors INT;
	
    SELECT COUNT(*) INTO num_available_doctors FROM doctor 
    WHERE specialization = d_specialization;
    
    IF num_available_doctors = 0 THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'No available doctors found';
    ELSE
		SELECT d.*
		FROM doctor AS d
		LEFT JOIN appointment_booking AS ab ON d.doctor_id = ab.doctor_id
		LEFT JOIN appointment AS a ON ab.appointment_id = a.appointment_id
		WHERE d.specialization = d_specialization
		AND (a.appointment_date IS NULL 
			OR NOT (a.appointment_date = appt_date AND a.appointment_time = appt_time));
	END IF;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS create_new_appointment;
DELIMITER $$
CREATE PROCEDURE create_new_appointment (
    IN student_id VARCHAR(7),
    IN doctor_id VARCHAR(7),
    IN appointment_date DATE,
    IN appointment_time TIME
)
BEGIN
    DECLARE appointment_id INT;

    -- Check if the student exists
    IF NOT EXISTS (SELECT * FROM student s WHERE s.student_id = student_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Student not found';
    END IF;

    -- Check if the doctor exists
    IF NOT EXISTS (SELECT * FROM doctor d WHERE d.doctor_id = doctor_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Doctor not found';
    END IF;

    -- Check if the appointment date is in the future
    IF appointment_date < CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Appointment date must be in the future';
    END IF;

    -- Check if the appointment time is already taken
    IF EXISTS (
        SELECT * FROM appointment_booking AS ab
        LEFT JOIN appointment AS a ON a.appointment_id = ab.appointment_id
        WHERE a.appointment_date = appointment_date
          AND a.appointment_time = appointment_time
          AND ab.doctor_id = doctor_id
    ) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Appointment time is already taken';
    END IF;

    -- Insert the new appointment into the appointment table
    INSERT INTO appointment (appointment_time, appointment_date) VALUES (appointment_time, appointment_date);
    SET appointment_id = LAST_INSERT_ID();

    -- Insert the new appointment booking into the appointment_booking table
    INSERT INTO appointment_booking (appointment_id, doctor_id, student_id) 
    VALUES (appointment_id, doctor_id, student_id);

    SELECT a.*, ab.student_id, ab.doctor_id FROM appointment_booking AS ab
    LEFT JOIN appointment AS a ON a.appointment_id = ab.appointment_id
    WHERE ab.appointment_id = appointment_id;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS get_doctor_id;
DELIMITER $$
CREATE PROCEDURE get_doctor_id(doc_first_name VARCHAR(15), doc_last_name VARCHAR(15))
BEGIN
	DECLARE doc_id VARCHAR(7);
    SELECT doctor_id INTO doc_id FROM doctor WHERE first_name = doc_first_name AND last_name = doc_last_name;

    IF doc_id IS NULL THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'The given dcotor is not found in the records.';
    ELSE
		SELECT * FROM doctor
		WHERE doctor_id = doc_id;
	END IF;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS delete_doctor;
DELIMITER $$
CREATE PROCEDURE delete_doctor(IN doc_id VARCHAR(7))
BEGIN
  DELETE FROM doctor WHERE doctor_id = doc_id;
  SELECT ROW_COUNT() AS rows_deleted;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS delete_appointment;
DELIMITER $$
CREATE PROCEDURE delete_appointment(IN app_id VARCHAR(7))
BEGIN
  DELETE FROM appointment_booking WHERE appointment_id = app_id;
  SELECT ROW_COUNT() AS rows_deleted;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS update_appointment_datetime;
DELIMITER $$
CREATE PROCEDURE update_appointment_datetime (
    IN appointment_id INT,
    IN new_date DATE,
    IN new_time TIME
)
BEGIN
    DECLARE appointment_exists INT;
    DECLARE conflict_count INT;
    
    SELECT COUNT(*) INTO appointment_exists FROM appointment WHERE appointment_id = appointment_id;
    IF appointment_exists = 0 THEN
       SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Appointment does not exist.';
	END IF;
    
	SET conflict_count = (
    SELECT COUNT(*)
    FROM appointment
    WHERE appointment_id != appointment_id_in
    AND appointment_date = new_appointment_date_in
    AND appointment_time = new_appointment_time_in);
    
    IF conflict_count = 0 THEN
        UPDATE appointment
        SET appointment_date = new_date, appointment_time = new_time
        WHERE appointment_id = appointment_id;
		SELECT 'Appointment updated successfully';
	ELSE 
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Appointment time conflicts with an existing appointment';
	END IF;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS get_appointments_by_month;
DELIMITER $$
CREATE PROCEDURE get_appointments_by_month()
BEGIN
  SELECT YEAR(appointment_date) AS year, MONTH(appointment_date) AS month, COUNT(*) AS appointment_count
  FROM appointment
  GROUP BY YEAR(appointment_date), MONTH(appointment_date)
  ORDER BY MONTH(appointment_date), YEAR(appointment_date);
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS add_doctor;
DELIMITER $$
CREATE PROCEDURE add_doctor (
  IN p_doctor_id VARCHAR(7),
  IN p_first_name VARCHAR(15),
  IN p_last_name VARCHAR(15),
  IN p_email_id VARCHAR(40),
  IN p_qualification VARCHAR(10),
  IN p_phone_number VARCHAR(10),
  IN p_specialization VARCHAR(40)
)
BEGIN
  DECLARE specialization_exists INT;
  DECLARE qualification_exists INT;
  DECLARE email_exists INT;
  DECLARE phone_exists INT;
  DECLARE id_exists INT;
  
  -- Check if specialization exists
  SELECT COUNT(*) INTO specialization_exists FROM specialization WHERE name = p_specialization;
  IF specialization_exists = 0 THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Specialization does not exist';
  END IF;
  
  -- Check if qualification exists
  SELECT COUNT(*) INTO qualification_exists FROM qualification WHERE abbreviation = p_qualification;
  IF qualification_exists = 0 THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Qualification does not exist';
  END IF;
  
  -- Check if email already exists
  SELECT COUNT(*) INTO email_exists FROM doctor WHERE email_id = p_email_id;
  IF email_exists > 0 THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Email already exists';
  END IF;
  
  -- Check if phone number already exists
  SELECT COUNT(*) INTO phone_exists FROM doctor WHERE phone_number = p_phone_number;
  IF phone_exists > 0 THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Phone number already exists';
  END IF;
  
  -- Check if doctor_id already exists
  SELECT COUNT(*) INTO id_exists FROM doctor WHERE doctor_id = p_doctor_id;
  IF id_exists > 0 THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Doctor ID already exists';
  END IF;
  
  -- Insert new doctor
  INSERT INTO doctor (doctor_id, first_name, last_name, email_id, qualification, phone_number, specialization)
  VALUES (p_doctor_id, p_first_name, p_last_name, p_email_id, p_qualification, p_phone_number, p_specialization);
  
  SELECT * FROM doctor
  WHERE doctor_id = p_doctor_id;
  
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS add_test_to_appointment;
DELIMITER $$
CREATE PROCEDURE add_test_to_appointment (
  IN lab_test_name TEXT,
  IN appt_id INT
)
BEGIN
  IF NOT EXISTS (SELECT * FROM appointment WHERE appointment_id = appt_id) THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Error: Appointment ID does not exist.';
  END IF;
  
  IF NOT EXISTS (SELECT * FROM lab_test WHERE test_name = lab_test_name) THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Error: Lab test does not exist.';
  END IF;
  
  INSERT INTO lab_test_for_appointment (test_name, appointment_id) VALUES (lab_test_name, appt_id);
  SELECT * FROM lab_test WHERE test_name = lab_test_name;
END$$
DELIMITER ;


# ------------------------------------- Triggers -------------------------------------
DROP TRIGGER IF EXISTS delete_doctor_trigger;
DELIMITER $$
CREATE TRIGGER delete_doctor_trigger
AFTER DELETE ON doctor
FOR EACH ROW
BEGIN
  DELETE FROM appointment_booking WHERE doctor_id = OLD.doctor_id;
END $$
DELIMITER ;

DROP TRIGGER IF EXISTS delete_lab_test_trigger;
DELIMITER $$
CREATE TRIGGER delete_lab_test_trigger
BEFORE DELETE ON appointment_booking
FOR EACH ROW
BEGIN
  DELETE FROM lab_test_for_appointment WHERE appointment_id = OLD.appointment_id;
END $$
DELIMITER ;


DROP TRIGGER IF EXISTS delete_appointment_booking_trigger;
DELIMITER $$
CREATE TRIGGER delete_appointment_booking_trigger
BEFORE DELETE ON appointment_booking
FOR EACH ROW
BEGIN
  DELETE FROM appointment WHERE appointment_id = OLD.appointment_id 
  AND NOT EXISTS (SELECT * FROM appointment_booking WHERE appointment_id = OLD.appointment_id);
END $$
DELIMITER ;


