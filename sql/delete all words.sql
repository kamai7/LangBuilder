Use langbuilder;

DELETE FROM WordsTypes;
DELETE FROM UsedRoots;
DELETE FROM Link;
DELETE FROM Definition;
DELETE FROM Translation;
DELETE FROM WordsLetters;
DELETE FROM Word;

SELECT * FROM Word LEFT JOIN WordsLetters ON (wordLId = wordId) LEFT JOIN UsedRoots ON (wordId = roWordId) LEFT JOIN WordsTypes ON (wordId = tyWordId) LEFT JOIN Link ON (wordId = lWordId)
LEFT JOIN Definition ON (wordId = dWordId) LEFT JOIN Translation ON (wordId = tWordId);

SELECT * FROM Word;