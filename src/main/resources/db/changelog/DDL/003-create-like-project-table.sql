create table if not exists liked_projects (
    username varchar(32) not null,
    project_id bigint references projects(id) on delete cascade,
    primary key (username, project_id)
);

comment on table liked_projects is 'Список понравившихся проектов';
comment on column liked_projects.username is 'Лайкнувший пользователь';
comment on column liked_projects.project_id is 'Понравившийся проект';