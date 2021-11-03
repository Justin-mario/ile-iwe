set Foreign_key_checks = 0;

truncate table learning_party;
truncate table authority;
truncate table instructor;

insert into learning_party(`id`, `email`, `password`, `enabled`)
values(123, 'gozie@gmail.com', 'gozie123', false ),
      (124, 'ozie@gmail.com', 'gozie123', false );


set foreign_key_checks = 1;