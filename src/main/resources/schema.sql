create table patient(
    id bigint not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    patronymic varchar(255),
    phone varchar(255),
    primary key (id)
);

create table doctor (
    id bigint not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    patronymic varchar(255),
    specialization varchar(255) not null,
    primary key (id)
);

create table recipe (
    id bigint not null,
    creation_date date,
    description varchar(255) not null,
    priority varchar(100) not null,
    validity_days integer not null,
    doctor_id bigint not null,
    patient_id bigint not null,
    primary key (id),
    constraint fk_doctor_recipe foreign key (doctor_id) references doctor,
    constraint fk__patient_recipe foreign key (patient_id) references patient
);

create sequence HIBERNATE_SEQUENCE AS bigint;