-- DELETE FROM locations; -- uncomment for a clean start
DELETE FROM locations where id > 1000000;

INSERT INTO locations (id, name, lat, lng, type)
VALUES (1000001, 'location1', -80, 170, 0),
       (1000002, 'location2', -70, -170, 1),
       (1000003, 'location3', 80, -170, 1),
       (1000004, 'location4', 70, 170, 0),
       (1000005, 'location5', 40, -30, 0),
       (1000006, 'location6', 30, 30, 1),
       (1000007, 'location7', -40, 30, 1),
       (1000008, 'location8', -30, -30, 0);
