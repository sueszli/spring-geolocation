-- remove previous demo data
DELETE FROM locations WHERE id > 1000000;

-- insert new demo data
INSERT INTO locations (id, name, lat, lng, type)
VALUES (1000001, "demo 1", 52.520008, 13.404954, "PREMIUM"),
       (1000002, "demo 2", -89.234234, 120.010132, "STANDARD"),
       (1000003, "demo 3", 10.000000, 10.000000, "PREMIUM"),
       (1000004, "demo 4", 20.000000, 20.000000, "STANDARD"),
       (1000005, "demo 5", 30.000000, 30.000000, "PREMIUM"),
       (1000006, "demo 6", 40.000000, 40.000000, "STANDARD"),
       (1000007, "demo 7", 50.000000, 50.000000, "PREMIUM"),
       (1000008, "demo 8", 60.000000, 60.000000, "STANDARD"),
       (1000009, "demo 9", 70.000000, 70.000000, "PREMIUM"),
       (1000010, "demo 10", 80.000000, 80.000000, "STANDARD");
