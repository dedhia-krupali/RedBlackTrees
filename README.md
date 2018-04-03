# RedBlackTrees

This project implements in Java a red-black tree data structure.

IDE used: Eclipse
Run the program on: Command line

1. Create an input file with commands of the format-
    Command_name:value

2. Save the input file in the folder which has the source code

3. On the command line first make project 4 your present working directory

4. Then compile the two classes Main.java and RedBlackTree.java using the following commands:

javac Main.java
javac RedBlackTree.java

5. Run the program using the command:
java Main input.txt output.txt

6. The output.txt has the generated result

My program executes completely, it throws and catches errors where required. 

Example input 1:

Integer
Insert:98
Insert:-68
Insert:55
Insert:45
PrintTree
Contains:45
Insert:84
Insert:32
Insert:132
Insert:45
PrintTree
Insert
hih

Example output:

True
True
True
True
55 -68 *45 98
True
True
True
True
False
55 32 *-68 *45 98 *84 *132
Error in Line: Insert
Error in Line: hih

Sample Input 2:

String
Insert:Ana
Insert:Owen
Insert:Pete
Insert:Leo
PrintTree
Contains:Owen
Insert:Nick
Insert:Maya
Insert:Leo
PrintTree

Sample output 2:

True
True
True
True
Owen Ana *Leo Pete
True
True
True
False
Owen *Leo Ana Nick *Maya Pete

Sample Input 3:

Students

Sample output 3:

Only works for objects Integers and Strings
