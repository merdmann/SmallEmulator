# SmallEmulator
SmallEmulator, this is a CPU Simulator for educaltional purposes only.

##status

This is the simple CPU simulator according to Simulate-CPU.docx
in the same archive as the rest of the complete source code.


##install

unpack the archive emulator.tar into a suitible directory. THe result 
will be a directory called emulator. To check this just run in your 
CLI the command java --version. On my System i am getting:

java 9.0.1
Java(TM) SE Runtime Environment (build 9.0.1+11)
Java HotSpot(TM) 64-Bit Server VM (build 9.0.1+11, mixed mode)

## how to run

Assuming that java is in your path 

This executes the code assembled from the file ../test.asm
 
merdmann  ~  eclipse-workspace  emulator > ls

$ tar xvf emulator.tar
$ cd emulator
$ ls
bin  emulator.tar  src  test.asm
$ cd bin
$ java emulator.Emulator ../test.asm

The emulator loads the given file (e.g test.asm) and executes immediatly
4 CPU instances.



##limits/TODOS

* The assember language is slightly different then the examples, Avoid
using blanks after ",", e.g. LOAD R0, 1  should be LOAD R0,1

* The whole code has not seen any systematic testing due to time constraints.

* For time contrain reasons, the output of the programm is not 100% identical with 
  original spec. I am considering nothing missing but is to be more a layout issue.
  SInce there is not time any to pollish due to time constraints.

* THe assembler is only accepting only comments which start st the begin of the line, e.g.

  ;; loading some register
  LOAD R1,R2 

  ;; but not
  LOAD R1, R2