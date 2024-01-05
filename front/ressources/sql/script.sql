CREATE TABLE `users` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255),
  `name` varchar(255),
  `password` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `rentals` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `surface` numeric,
  `price` numeric,
  `picture` varchar(255),
  `description` varchar(2000),
  `owner_id` integer NOT NULL,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `messages` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `rental_id` integer,
  `user_id` integer,
  `message` varchar(2000),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE UNIQUE INDEX `users_index` ON `users` (`email`);

-- ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `rentals` (`owner_id`);
ALTER TABLE `rentals` ADD CONSTRAINT `fk_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);

-- ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `messages` (`user_id`);
ALTER TABLE `messages` ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

-- ALTER TABLE `rentals` ADD FOREIGN KEY (`id`) REFERENCES `messages` (`rentals_id`);
ALTER TABLE `messages` ADD CONSTRAINT `fk_rentals_id` FOREIGN KEY (`rentals_id`) REFERENCES `rentals` (`id`);




INSERT INTO users (email, name, password, created_at, updated_at)
VALUES
  ('john.doe@example.com', 'John Doe', 'hashed_password_1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (email, name, password, created_at, updated_at)
VALUES
  ('jane.smith@example.com', 'Jane Smith', 'hashed_password_2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (email, name, password, created_at, updated_at)
VALUES
  ('bob.jones@example.com', 'Bob Jones', 'hashed_password_3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (email, name, password, created_at, updated_at)
VALUES
  ('alice.wonder@example.com', 'Alice Wonderland', 'hashed_password_4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (email, name, password, created_at, updated_at)
VALUES
  ('charlie.brown@example.com', 'Charlie Brown', 'hashed_password_5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




INSERT INTO rentals (name, surface, price, picture, description, owner_id, created_at, updated_at)
VALUES
  ('Appartement moderne', 80, 1200.00, 'appartement.jpg', 'Bel appartement moderne avec vue sur la ville.', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO rentals (name, surface, price, picture, description, owner_id, created_at, updated_at)
VALUES
  ('Maison spacieuse', 200, 2500.00, 'maison.jpg', 'Grande maison avec jardin et piscine.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO rentals (name, surface, price, picture, description, owner_id, created_at, updated_at)
VALUES
  ('Studio en centre-ville', 40, 800.00, 'studio.jpg', 'Studio confortable au cœur de la ville.', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO rentals (name, surface, price, picture, description, owner_id, created_at, updated_at)
VALUES
  ('Villa de luxe', 400, 5000.00, 'villa.jpg', 'Magnifique villa de luxe avec vue sur l''océan.', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO rentals (name, surface, price, picture, description, owner_id, created_at, updated_at)
VALUES
  ('Appartement avec terrasse', 60, 1000.00, 'appartement_terrasse.jpg', 'Appartement lumineux avec grande terrasse.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO messages (rental_id, user_id, message, created_at, updated_at)
VALUES (1, 2, 'Bonjour, je suis intéressé par votre location.', '2023-12-14 12:30:00', '2023-12-14 12:30:00');

INSERT INTO messages (rental_id, user_id, message, created_at, updated_at)
VALUES (2, 3, 'Pouvez-vous me donner plus de détails sur le prix ?', '2023-12-14 13:15:00', '2023-12-14 13:15:00');

INSERT INTO messages (rental_id, user_id, message, created_at, updated_at)
VALUES (3, 1, 'La location est disponible à partir de quand ?', '2023-12-14 14:00:00', '2023-12-14 14:00:00');

INSERT INTO messages (rental_id, user_id, message, created_at, updated_at)
VALUES (1, 4, 'Je suis intéressé, pouvons-nous organiser une visite ?', '2023-12-14 15:00:00', '2023-12-14 15:00:00');

INSERT INTO messages (rental_id, user_id, message, created_at, updated_at)
VALUES (2, 1, 'Le prix est négociable ?', '2023-12-14 16:30:00', '2023-12-14 16:30:00');

INSERT INTO messages (rental_id, user_id, message, created_at, updated_at)
VALUES (3, 2, 'Quelles sont les conditions de location ?', '2023-12-14 17:45:00', '2023-12-14 17:45:00');
