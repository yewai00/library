use booklib;

CREATE TABLE IF NOT EXISTS `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `cover_image` varchar(255) DEFAULT NULL,
  `genres` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `rating` tinyint DEFAULT NULL,
  `pdf_link` varchar(255) DEFAULT NULL,
  `created_at` TIMESTAMP DEFAULT NULL,
  `updated_at` TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (`id`)
); 

CREATE TABLE IF NOT EXISTS `commenters` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_id` bigint DEFAULT NULL,
  `commenter_id` bigint DEFAULT NULL,
  `comment_text` text,
  `created_at` TIMESTAMP DEFAULT NULL,
  `updated_at` TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`commenter_id`) REFERENCES `commenters` (`id`) ON DELETE CASCADE)

