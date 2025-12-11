use railway;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username NVARCHAR(20),
    hashedpsswd NVARCHAR(64),
    email NVARCHAR(20)
);
CREATE TABLE task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name NVARCHAR(30),
    plannedD NVARCHAR(12),
    user_id int,
    status boolean,
	CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);
select * from task;
select * from users;

INSERT INTO users (username, hashedpsswd, email) VALUES
('juan23', SHA2('pass123', 256), 'juan23@example.com'),
('maria_l', SHA2('secure456', 256), 'maria_l@example.com'),
('carlos_r', SHA2('clave789', 256), 'carlos_r@example.com'),
('ana_p', SHA2('mypwd321', 256), 'ana_p@example.com'),
('luis_g', SHA2('test654', 256), 'luis_g@example.com');

INSERT INTO task (name, plannedD, user_id, status) VALUES
-- Tareas de Juan (user_id = 1)
('Comprar despensa', '2025-11-27', 1, 0),
('Ir al gimnasio', '2025-11-28', 1, 1),
('Pagar servicios', '2025-11-29', 1, 0),

-- Tareas de María (user_id = 2)
('Llamar a mamá', '2025-11-27', 2, 1),
('Revisar correos', '2025-11-28', 2, 0),
('Preparar presentación', '2025-11-30', 2, 1),

-- Tareas de Carlos (user_id = 3)
('Sacar al perro', '2025-11-27', 3, 0),
('Estudiar inglés', '2025-11-28', 3, 1),
('Hacer limpieza', '2025-11-29', 3, 0),

-- Tareas de Ana (user_id = 4)
('Leer un libro', '2025-11-27', 4, 1),
('Ir al médico', '2025-11-28', 4, 0),
('Organizar archivos', '2025-11-30', 4, 1),

-- Tareas de Luis (user_id = 5)
('Cocinar cena', '2025-11-27', 5, 0),
('Hacer ejercicio', '2025-11-28', 5, 1),
('Lavar ropa', '2025-11-29', 5, 0);


select * from users;
select * from task;