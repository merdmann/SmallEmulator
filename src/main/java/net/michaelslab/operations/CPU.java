//
// Copyright (c) 2018 Michael Erdmann
//
// Permission is hereby granted, to any person obtaining a copy of this software and associated
// documentation files (the "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
// copies of the Software, and to permit persons to whom the Software is furnished to do so,
// subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all copies or
// substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
// NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
// DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
//
// THis interface defines all opcodes which are generated as a result of the
// assembly.

//
package net.michaelslab.operations;

import net.michaelslab.memory.Memory;

public class CPU  implements Runnable {
    Thread thread;
    int nbr;
    Memory memory;

    int SP;
    int PC;
    int flags;
    int []r;

    final int INV_OPCODE = 1;
    final int STOPPED    = 2;


    public CPU( int nbr, Memory mem, int pc, int sp, int [] regs) {
        this.nbr = nbr;
        this.memory = mem;
        this.PC = pc;
        this.SP = sp;
        this.r = regs;
    }

    /*
     * start the CPU for processor nbr with a given pc /sp and runs until
     * the end operation
     */
    public void start(int nbr, int[] regs) {

        Runnable cpu = new CPU(nbr, memory, PC, SP, regs);
        this.thread = new Thread(cpu);

        this.thread.start();

        try {
            this.thread.join();
            flags = STOPPED;
        }
        catch(Exception ex) {
            System.out.println("Interupted");
        }
    }

    //protected void finalize()  throws Throwable {
    //	System.out.println("finalizer called");
    //}

    public int getRegister(int rr) {
        System.out.println("getRegister r[" + rr + "] = " + this.r + "object" + this.r );
        return this.r[rr];
    }

    public synchronized int getRegister(String name) {
        System.out.println("getRegister[" + name + "]=" + this.r[Memory.register_id(name)]);

        return( this.r[Memory.register_id(name)] );
    }

    public synchronized boolean IsStoppedState () {
        return this.flags == STOPPED;
    }

    @Override
    public void run() {
        while( flags != INV_OPCODE && flags != STOPPED) {

            System.out.println("* CPU :" + this.nbr);
            System.out.println("* EXEC PC : " + PC + "  r0:" + r[0] + " r1:" + r[1] + " r2:" + r[2] + " r3:" + r[3]);

            memory.dump( PC, 8 );

            switch( memory.get(PC) ) {

                case IOPCode.opc_add : {
                    int r1 = memory.get(PC+1);
                    int r2 = memory.get(PC+2);

                    r[r1] = r[r1] + r[r2];

                    PC = PC + 3;  // length including opcodes and args in words
                    break;
                }

                case IOPCode.opc_mult: {
                    int r1 = memory.get(PC+1);
                    int r2 = memory.get(PC+2);

                    r[r1] = r[r1] + r[r2];

                    PC = PC + 3;
                    System.out.println("");

                    break;
                }

                case IOPCode.opc_store: {
                    int location = memory.get(PC+2);
                    int r1 = memory.get(PC+1);

                    memory.set(location, r[r1]);

                    PC = PC + 2;

                    break;
                }

                case IOPCode.opc_fetch: {
                    int location = memory.get(PC+2);
                    int r1 = memory.get(PC+1);

                    r[r1] = memory.get(location);

                    PC = PC + 2;

                    break;
                }

                case IOPCode.opc_equ: {
                    int r1 = memory.get(PC+1);
                    int r2 = memory.get(PC+2);
                    int r3 = memory.get(PC+3);

                    r[r3] = 1;
                    r[r3] = (r[r1] == r[r1]) ? 0 : 1;

                    PC = PC + 4;

                    break;
                }

                case IOPCode.opc_push: {
                    int r1 = memory.get(PC+1);

                    SP = SP - 1;
                    memory.set(SP, r[r1]);

                    PC = PC + 2;

                    break;
                }


                case IOPCode.opc_pop: {
                    int r1 = memory.get(PC+1);

                    memory.get(SP);

                    SP = SP - 1;

                    PC = PC + 2;

                    break;
                }

                case IOPCode.opc_goto: {
                    int r1 = memory.get(PC+1);
                    PC = PC + r[r1];
                    break;
                }

                case IOPCode.opc_load: {
                    int r1    = memory.get(PC+1);
                    int value = memory.get(PC+2);

                    this.r[r1] = value;
                    System.out.println(" PC : " + PC + "  r0:" + r[0] + " r1:" + r[1] + " r2:" + r[2] + " r3:" + r[3]);

                    PC = PC + 3;

                    break;
                }

                case IOPCode.opc_out: {
                    int r1 = memory.get(PC+1);
                    System.out.println( "* OUT: Register r["+r1+"] : " + r[r1] );
                    memory.dump(PC, 16);
                    PC = PC + 2;
                    break;
                }

                case IOPCode.opc_if: {
                    int r1 = memory.get(PC+1);
                    int r2 = memory.get(PC+2);

                    if( r[r1] != 0)
                        PC = PC + r[r2];

                    PC = PC + 3;

                    break;
                }


                case IOPCode.opc_debug: {
                    int val = memory.get(PC+1);

                    switch( val ) {
                        case 1:
                            System.out.format("*DEBUG CPU:" + this.nbr);
                            System.out.format(" PC " + PC );
                            System.out.println(" SP " + SP );
                            System.out.println(" PC : " + PC + "  r0:" + r[0] + " r1:" + r[1] + " r2:" + r[2] + " r3:" + r[3]);
                            //memory.dump(PC, 32);
                            break;

                        case 2:
                            memory.dump(PC, 32);
                            break;
                        case 3:
                            memory.dump(SP, 32);
                            break;
                    }

                    PC = PC + 2;
                    break;
                }

                case IOPCode.opc_end : {
                    System.out.println("**** End\n");
                    this.flags = STOPPED;
                    break;
                }

                default:
                    flags = INV_OPCODE;
                    System.out.println("invalid op code at " + PC + "= OPC:" + memory.get(PC) );
                    for(int i=0; i<8; ++i)
                        System.out.print(memory.get(PC+i));
                    break;

            } /* end swuitch */
        } /* end while */
    } /* end run */
}