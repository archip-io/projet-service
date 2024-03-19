create table if not exists projects (
    id bigserial primary key,
    owner_username varchar(32) not null,
    project_name varchar(80) not null,
    description text,
    is_private boolean not null default false,
    unique (owner_username, project_name)
);

comment on table projects is 'Проекты';
comment on column projects.owner_username is 'Владелец проекта';
comment on column projects.project_name is 'Название проекта';
comment on column  projects.description is 'Описание проекта';
comment on column projects.is_private is 'Флаг приватного проекта'