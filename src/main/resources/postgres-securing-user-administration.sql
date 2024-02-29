alter table users add column user_role varchar(20);

update users set user_role = 'ROLE_MEMBER';

update users set user_role = 'ROLE_ADMIN'
 where username = 'jbrameg';
