INSERT INTO artist (artist_id, artist_name, artist_genre)
VALUES
    (1,'Green Growers','REGGAE'),
    (2,'Face Eater', 'METAL');

INSERT INTO song (song_id, song_title, song_length, artist_id, song_genre)
VALUES
    (1,'Red Eye Jedi',4.20,1,'REGGAE'),
    (2, 'How High?',4.20,1,'REGGAE'),
    (3,'Miss. Mary Jane',4.20,1,'REGGAE'),

    (4,'Hail Satan',7.06,2,'METAL'),
    (5,'Forbidden Fruit',7.06,2,'METAL'),
    (6,'The Beast System',7.06,2,'METAL');

INSERT INTO album (album_id, album_title, album_length, album_year, tracks, artist_id)
VALUES
    (1,'Praise Jah',13,2000,3,1),
    (2,'The Devil''s Greatest Trick',21.18,2005,3,2);

INSERT INTO album_song (album_id, song_id)
VALUES
    (1,1),
    (1,2),
    (1,3),

    (2,4),
    (2,5),
    (2,6);

INSERT INTO app_user (user_id, username)
VALUES
    (1,'Kurt'),
    (2,'Dave');

ALTER TABLE artist ALTER COLUMN artist_id RESTART WITH 100;
ALTER TABLE song ALTER COLUMN song_id RESTART WITH 100;
ALTER TABLE album ALTER COLUMN album_id RESTART WITH 100;
ALTER TABLE app_user ALTER COLUMN user_id RESTART WITH 100;