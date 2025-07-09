drop table if exists WordsTypes;
drop table if exists Type;
drop table if exists UsedRoots;
drop table if exists Links;
drop table if exists Definition;
drop table if exists Translation;
drop table if exists WordsLetters;
drop table if exists Word;
drop table if exists Letter;
drop database if exists LangEngine;

create database LangEngine;

use LangEngine;

/*
letters which will be used in yout language
*/
create table Letter(
	letterId int,
	letter varchar(10) not null,
	letterAscii varchar(15) not null,
    
    -- contraintes
    CONSTRAINT pk_letter primary key(letterId),
    CONSTRAINT uq_letter unique(letter),
    CONSTRAINT uq_letterAscii unique(letterAscii)
);

create table Word(
	wordId int,
    emotional float not null,
    formality float not null,
    vulgarity float not null,
    isUsable bool not null,
    
    -- contraintes
    CONSTRAINT pk_word primary key(wordId),
    CONSTRAINT ck_emotional CHECK (emotional >= 0 and emotional <= 1),
    CONSTRAINT ck_formality CHECK (formality >= 0 and formality <= 1),
    CONSTRAINT ck_vulgarity CHECK (vulgarity >= 0 and vulgarity <= 1)
);

/*
stores letters which compose words
*/
create table WordsLetters(
	wordLId int,
    position int,
    letterWId int not null,
    
    -- contraintes
    FOREIGN KEY (wordLId) REFERENCES Word(wordId),
    FOREIGN KEY (letterWId) REFERENCES Letter(LetterId),
    CONSTRAINT pk_WordsLetters PRIMARY KEY (wordLId, position),
    CONSTRAINT ck_position CHECK (position > 0)
);

/*
the traduction of words in your real language
*/
create table Translation(
	tWordId int,
    translation varchar(60),
    
    -- contraintes
    FOREIGN KEY (tWordId) REFERENCES Word(wordId),
    CONSTRAINT pk_translation PRIMARY KEY(tWordId, translation)
);

/*
definition or definitions of each words of the language
*/
create table Definition(
	dWordId int,
    def varchar(200),
    
    -- contraintes
    FOREIGN KEY (dWordId) REFERENCES Word(wordId),
    CONSTRAINT pk_def PRIMARY KEY(dWordId, def)
);

/*
stores links between word to make a giant graph of the whole language
like "oak" will be linked to "tree"
*/
create table Links(
	lWordId int,
    linkedWordId int,
    
    -- contraintes
    FOREIGN KEY (lWordId) REFERENCES Word(wordId),
    CONSTRAINT pk_Links PRIMARY KEY(lWordId, linkedWordId)
);

/*
stores roots which are used in words
*/
create table UsedRoots(
	roWordId int,
    usedRootId int,
    
    -- contraintes
    FOREIGN KEY (roWordId) REFERENCES Word(wordId),
    FOREIGN KEY (usedRootId) REFERENCES Word(wordId),
    CONSTRAINT pk_usedRoots PRIMARY KEY(roWordId, usedRootId)
);

create table Type(
	typeId int not null,
    label varchar(20) not null,
    parentId int,
    rootId int,
    position int,
    
    -- contraintes
    CONSTRAINT pk_Type primary key(typeId),
    FOREIGN KEY (rootId) REFERENCES Word(wordId),
    FOREIGN KEY (parentId) REFERENCES Type(typeId),
    CONSTRAINT ck_TypePosition CHECK(position IN (0,1,2)),
    CONSTRAINT uq_label UNIQUE(label)
);

create table WordsTypes(
	tyWordId int not null,
    wordTypeId int not null,
    
    -- contraintes
    FOREIGN KEY (tyWordId) REFERENCES Word(wordId),
    FOREIGN KEY (wordTypeId) REFERENCES Type(typeId)
);