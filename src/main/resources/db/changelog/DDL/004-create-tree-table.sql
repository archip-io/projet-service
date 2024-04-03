create table if not exists tree (
    id uuid default gen_random_uuid() PRIMARY KEY,
    file_name varchar(255) not null,
    file_id varchar(255),
    parent_id uuid references tree(id) on delete cascade
);

comment on table tree is 'Дерево проектов';
comment on column tree.file_name is 'Название узла / листа';
comment on column tree.file_id is 'id файла';
comment on column tree.parent_id is 'Ссылка на родителя';