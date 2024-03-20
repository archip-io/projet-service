create table if not exists tags (
    id bigserial primary key,
    tag varchar(20) unique not null
);

comment on table tags is 'Тэги проектов';
comment on column tags.tag is 'Название тэга';