create function create_tree_project_after_create_project()
    returns trigger as $$
declare
    treeid integer;
begin
    insert into tree (file_name, parent_id) values (NEW.project_name, null) returning id into treeid;
    insert into project_tree (project_id, tree_id) values (NEW.id, treeid);
    return NEW;
end;
$$ language plpgsql;

create trigger insert_project_trigger
after insert on projects
for each row
execute procedure create_tree_project_after_create_project();