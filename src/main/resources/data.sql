INSERT INTO artist (artist_id, artist_name, artist_genre)
VALUES
    (1,'Meshuggah','METAL'),
    (2,'Bob Marley & The Wailers','REGGAE'),
    (3,'Thirty Seconds to Mars','ROCK'),
    (4,'Ricky Gervais','COMEDY'),
    (5,'Johnny Depp','ROCK'),
    (6,'Jamie Fox','POP'),
    (7,'Flea','ROCK'),
    (8,'Red Hot Chili Peppers','ROCK');

INSERT INTO song (song_id, song_title, song_length_in_seconds, artist_id, song_genre)
VALUES
    (1,'Combustion',248,1,'METAL'),
    (2,'Electric Red',351,1,'METAL'),
    (3,'Bleed',442,1,'METAL'),

    (4,'Easy Skanking',178,2,'REGGAE'),
    (5,'Kaya',195,2,'REGGAE'),
    (6,'Is This Love',232,2,'REGGAE'),

    (7,'Escape',143,3,'ROCK'),
    (8,'Night Of The Hunter',340,3,'ROCK'),
    (9,'Kings And Queens',347,3,'ROCK'),

    (10,'Average Sized Penis', 325,4,'COMEDY');

INSERT INTO album (album_id, album_title, album_length, album_year, tracks, artist_id)
VALUES
    (1,'Obzen',1041,2008,3,1),
    (2,'Kaya',605,1978,3,2),
    (3,'This Is War',830,2009,3,3),
    (4,'Legend - The Best Of Bob Marley And The Wailers',4320,2002,16,2);

INSERT INTO album_song (album_id, song_id)
VALUES
    (1,1),
    (1,2),
    (1,3),

    (2,4),
    (2,5),
    (2,6),

    (3,7),
    (3,8),
    (3,9);

INSERT INTO app_user (user_id, username)
VALUES
    (1,'Kurt'),
    (2,'Dave');

ALTER TABLE artist ALTER COLUMN artist_id RESTART WITH 100;
ALTER TABLE song ALTER COLUMN song_id RESTART WITH 100;
ALTER TABLE album ALTER COLUMN album_id RESTART WITH 100;
ALTER TABLE app_user ALTER COLUMN user_id RESTART WITH 100;