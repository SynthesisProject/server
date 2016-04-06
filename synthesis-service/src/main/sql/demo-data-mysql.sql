-- Insert a managed module
INSERT INTO managed_module (ID, MODULE_ID, LAST_SYNC, ACTIVE, LAST_UPDATED, REPOSITORY) VALUES(1, 'Synthesis', NULL, 1, "2015-01-01 00:00:00", "Sakai");

-- Insert tools
INSERT INTO tool (id, name, title, description, on_menu) VALUES (1, 'resources', 'Resources', 'Sakai resources', 'y');
INSERT INTO tool (id, name, title, description, on_menu) VALUES (2, 'announcements', 'Announcement', 'Sakai Announcements', 'y');
INSERT INTO tool (id, name, title, description, on_menu) VALUES (3, 'schedule', 'Calendar', 'Calendar', 'y');


-- Map tools to module
INSERT INTO module_tools (ID, MODULE_ID, TOOL_ID) VALUES (1, 1, 1);
INSERT INTO module_tools (ID, MODULE_ID, TOOL_ID) VALUES (2, 1, 2);
INSERT INTO module_tools (ID, MODULE_ID, TOOL_ID) VALUES (3, 1, 3);

-- Dummy device
INSERT INTO push_devices(id,uuid,model,pst_ts,regid,platform,usr,ver_nbr) VALUES(1, 'abcd', 'Samsung I9300', "2015-09-30 12:00:00", 'APA91bG-5cqYqCHY6NJl8jmih7C96SVNhFKEUeQQTAogI7KUklxxyk-2Z6nvVZxPdvxCG2XvStyCj7_wi1HdeXYNH-FLgqx67QjHc2CHFOvFOCGqk0fB8iLnJ6U-dDL6rNwOfp-GDaNI', 'Android', 'student', 1);
INSERT INTO push_devices(id,uuid,model,pst_ts,regid,platform,usr,ver_nbr) VALUES(2, 'qaze', 'iPad Mini', "2015-09-30 12:00:00", 'c13904dc1de837f1cd65dbac9e106ecb5ad04391abce40c6f4dad297c855c597', 'iOS', 'student', 1);


INSERT INTO push_msg( id, emr, msg, pst_ts, sndr, ttl, url, ver_nbr) VALUES (1, 1, 'This is a test message', "2015-09-30 12:00:00", 'AFL-35', 'New Announcement', '#/announcement/xx', '1');

INSERT INTO push_pref (id, enb,pst_ts,sender,ver_nbr,device_id)VALUES(1,1,"2015-01-01 00:00:00","module.Synthesis",1,1);
INSERT INTO push_pref (id, enb,pst_ts,sender,ver_nbr,device_id)VALUES(2,1,"2015-01-01 00:00:00","module.Synthesis",1,2);

-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
