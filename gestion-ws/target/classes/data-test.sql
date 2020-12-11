--type biere
insert into type_biere (id_type_biere, nom, description, date_creation, date_mis_a_jour)
values (nextval('type_biere_id_seq'), 'Bière Test Nom', 'belle lager', current_timestamp, current_timestamp);

insert into type_biere (id_type_biere, nom, description, date_creation, date_mis_a_jour)
values (nextval('type_biere_id_seq'), 'Bière Test Nom 2', 'belle lager 2', current_timestamp, current_timestamp);

insert into type_biere (id_type_biere, nom, description, date_creation, date_mis_a_jour)
values (nextval('type_biere_id_seq'), 'IPA', 'description belle ipa', current_timestamp, current_timestamp);

insert into type_biere (id_type_biere, nom, description, date_creation, date_mis_a_jour)
values (nextval('type_biere_id_seq'), 'Double IPA', 'description double ipa', current_timestamp, current_timestamp);

insert into type_biere (id_type_biere, nom, description, date_creation, date_mis_a_jour)
values (nextval('type_biere_id_seq'), 'White IPA', 'description white ipa', current_timestamp, current_timestamp);

insert into type_biere (id_type_biere, nom, description, date_creation, date_mis_a_jour)
values (nextval('type_biere_id_seq'), 'Stout', 'description belle stout', current_timestamp, current_timestamp);

insert into type_biere (id_type_biere, nom, description, date_creation, date_mis_a_jour)
values (nextval('type_biere_id_seq'), 'Sour', 'description belle sour', current_timestamp, current_timestamp);

insert into type_biere (id_type_biere, nom, description, date_creation, date_mis_a_jour)
values (nextval('type_biere_id_seq'), 'Rousse', 'description belle rousse', current_timestamp, current_timestamp);

insert into type_biere (id_type_biere, nom, description, date_creation, date_mis_a_jour)
values (nextval('type_biere_id_seq'), 'Lager', 'description belle lager', current_timestamp, current_timestamp);

insert into type_biere (id_type_biere, nom, description, date_creation, date_mis_a_jour)
values (nextval('type_biere_id_seq'), 'Dry Stout', 'description dry stout', current_timestamp, current_timestamp);

--biere
insert into biere (id_biere, nom, origine, taux_alcool, ibu, amertume, description, id_type_biere, date_creation, date_mis_a_jour) 
values (nextval('biere_id_seq'), 'The best Stout', 'Canada', 7.5, 35, 'MOYENNE', 'description the best stout', 10, current_timestamp, current_timestamp);

insert into biere (id_biere, nom, origine, taux_alcool, ibu, amertume, description, id_type_biere, date_creation, date_mis_a_jour)
values (nextval('biere_id_seq'), 'The best IPA', 'Brésil', 6.5, 55, 'MOYENNE', 'description the best ipa', 2, current_timestamp, current_timestamp);

insert into biere (id_biere, nom, origine, taux_alcool, ibu, amertume, description, id_type_biere, date_creation, date_mis_a_jour)
values (nextval('biere_id_seq'), 'Double IPA', 'Argentine', 8.5, 65, 'FORTE', 'description the best double ipa', 1, current_timestamp, current_timestamp);

insert into biere (id_biere, nom, origine, taux_alcool, ibu, amertume, description, id_type_biere, date_creation, date_mis_a_jour)
values (nextval('biere_id_seq'), 'The best Lager', 'Brésil', 5.5, 25, 'FAIBLE', 'description lager', 2, current_timestamp, current_timestamp);

insert into biere (id_biere, nom, origine, taux_alcool, ibu, amertume, description, id_type_biere, date_creation, date_mis_a_jour)
values (nextval('biere_id_seq'), 'The best Dry Stout', 'Canada', 6.5, 35, 'FORTE', 'description dry stout', 3, current_timestamp, current_timestamp);

insert into biere (id_biere, nom, origine, taux_alcool, ibu, amertume, description, id_type_biere, date_creation, date_mis_a_jour)
values (nextval('biere_id_seq'), 'The best Rousse', 'Argentine', 5.0, 40, 'MOYENNE', 'description the best rousse', 4, current_timestamp, current_timestamp);

insert into biere (id_biere, nom, origine, taux_alcool, ibu, amertume, description, id_type_biere, date_creation, date_mis_a_jour)
values (nextval('biere_id_seq'), 'The best Blonde', 'Canada', 4.5, 25, 'FAIBLE', 'description blonde', 5, current_timestamp, current_timestamp);

insert into biere (id_biere, nom, origine, taux_alcool, ibu, amertume, description, id_type_biere, date_creation, date_mis_a_jour)
values (nextval('biere_id_seq'), 'The best Blanche', 'Canada', 4.5, 30, 'FAIBLE', 'description blanche', 6, current_timestamp, current_timestamp);

--prix
insert into prix_biere (id_prix_biere, prix, id_biere, date_creation, date_mis_a_jour)
values (nextval('prix_biere_id_seq'), 'Médaille dor 2019', 1, current_timestamp, current_timestamp);

insert into prix_biere (id_prix_biere, prix, id_biere, date_creation, date_mis_a_jour)
values (nextval('prix_biere_id_seq'), 'Médaille dor 2018', 1, current_timestamp, current_timestamp);

insert into prix_biere (id_prix_biere, prix, id_biere, date_creation, date_mis_a_jour)
values (nextval('prix_biere_id_seq'), 'Médaille dor 2017', 1, current_timestamp, current_timestamp);

insert into prix_biere (id_prix_biere, prix, id_biere, date_creation, date_mis_a_jour)
values (nextval('prix_biere_id_seq'), 'Médaille dargent 2019', 2, current_timestamp, current_timestamp);

insert into prix_biere (id_prix_biere, prix, id_biere, date_creation, date_mis_a_jour)
values (nextval('prix_biere_id_seq'), 'Médaille dargent 2018', 2, current_timestamp, current_timestamp);

insert into prix_biere (id_prix_biere, prix, id_biere, date_creation, date_mis_a_jour)
values (nextval('prix_biere_id_seq'), 'Médaille dargent 2017', 2, current_timestamp, current_timestamp);

insert into prix_biere (id_prix_biere, prix, id_biere, date_creation, date_mis_a_jour)
values (nextval('prix_biere_id_seq'), 'Médaille de bronze 2019', 3, current_timestamp, current_timestamp);

insert into prix_biere (id_prix_biere, prix, id_biere, date_creation, date_mis_a_jour)
values (nextval('prix_biere_id_seq'), 'Médaille de bronze 2018', 3, current_timestamp, current_timestamp);

insert into prix_biere (id_prix_biere, prix, id_biere, date_creation, date_mis_a_jour)
values (nextval('prix_biere_id_seq'), 'Médaille de bronze 2017', 3, current_timestamp, current_timestamp);

insert into prix_biere (id_prix_biere, prix, id_biere, date_creation, date_mis_a_jour)
values (nextval('prix_biere_id_seq'), 'World beer awards', 4, current_timestamp, current_timestamp);

insert into prix_biere (id_prix_biere, prix, id_biere, date_creation, date_mis_a_jour)
values (nextval('prix_biere_id_seq'), 'Best beer du Brésil', 5, current_timestamp, current_timestamp);