create table if not exists projects_tags (
    tag_id bigint references tags(id) on delete cascade,
    project_id bigint references projects(id) on delete cascade,
    primary key (tag_id, project_id)
);

comment on table projects_tags is 'Таблица тэгов проектов';