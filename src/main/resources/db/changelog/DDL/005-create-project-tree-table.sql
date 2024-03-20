create table if not exists project_tree (
    project_id bigint references projects(id) on delete cascade,
    tree_id uuid references tree(id) on delete restrict
);

comment on table project_tree is 'Сопоставление дерева и корневого узла';
comment on column project_tree.project_id is 'id проекта';
comment on column project_tree.tree_id is 'id корневого узла';