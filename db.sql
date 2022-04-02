-- Database: patientbdd

-- DROP DATABASE patientbdd;

CREATE DATABASE patientbdd

    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;


CREATE SEQUENCE public.patient_id_seq;


CREATE TABLE public.patient (
                id INTEGER NOT NULL DEFAULT nextval('public.patient_id_seq'),
                prenom VARCHAR NOT NULL,
                nom VARCHAR NOT NULL,
                naissance DATE NOT NULL,
                genre VARCHAR NOT NULL,
                adresse VARCHAR NOT NULL,
                telephone VARCHAR NOT NULL,
                CONSTRAINT id PRIMARY KEY (id)
);


ALTER SEQUENCE public.patient_id_seq OWNED BY public.patient.id;