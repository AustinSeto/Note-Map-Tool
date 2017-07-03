# Note-Map-Tool

A tool to help generate note maps for a rhythm game

## What this does

This tool will assist a user in creating a note map for a rhythm game. Using the keyboard, the user can compose music beat by beat. Songs can be written to or read from text files in the format described below.

## File format

The first line of the file has three integer values seperated by a single space, `n` and `s` and `l`. `n` denotes how many different notes can appear. This does not include chords. `s` denotes the speed of the song in beats per minute. `l` denotes how many beats are in the song.

The next `n` lines each contain `l` digits. These digits may be seperated by whitespace, but it is not mandatory. Each beat is represented by a single digit. A zero indicates that the given note for that line will not be played on that beat. A nonzero value indicates that the note will be played on that beat. If a line has less than `l` digits, it will be assumed that the rest of the digits are zero. A line with more than `l` digits will have every digit past the `l`th one ignored. 
