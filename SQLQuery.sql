drop database if exists tasks;
create database tasks;
use tasks;
CREATE TABLE task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name NVARCHAR(20),
    plannedD NVARCHAR(12),
    status boolean
);
INSERT INTO task (name, plannedD, status) VALUES
('Login UI', '2025-11-07', true),
('API Setup', '2025-11-08', false),
('DB Schema', '2025-11-09', false),
('Testing', '2025-11-10', true),
('Deployment', '2025-11-11', false);
select *from task;

