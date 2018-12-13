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

package net.michaelslab.memory;

import java.util.StringTokenizer;

import net.michaelslab.operations.IOPCode;

public class Memory {
	int storage[ ];
	int text_size = 0;
	public int wp = 0;
	
	public Memory(int size, int sp_size) {
		text_size = size;
		
		storage = new int [size + sp_size];
	}
	
	int put(int byteCode) {
		if( wp < text_size ) {
			storage[wp++] = byteCode;
		}
		return wp;
	}
	
	public int org( int address ) {
		System.out.println("\nORG(" + address + ");\n");
		wp = address;
		
		return wp;
	}
	
	public int get(int addr) {
		return storage[addr];
	}
	
	public void set(int location, int value) {
		storage[location] = value;
	}
	
	// create a memory with the assembler code from the give file
	public void assemble_add( int r1, int r2 ) {
		put( IOPCode.opc_add);
		put( r1);
		put( r2);
	}
	
	public void assemble_mult( int r1, int r2 ) {
		put(IOPCode.opc_mult);
		put(r1);
		put(r2);
	}
	
	public void assemble_store( int addr) {
		put(IOPCode.opc_store);
		put(addr);
	}
	
	void assemble_fetch( int r1, int addr) {
		put(IOPCode.opc_fetch);
		put( r1 );
		put(addr);
	}
	
	public void assemble_equ( int r1, int r2, int r3 ) {
		put(IOPCode.opc_equ);
		put(r1);
		put(r2);
		put(r3);
	}
	
	public void assemble_push(int r1) {
		put(IOPCode.opc_push);
		put(r1);
	}
	
	public void assemble_pop(int r1) {
		put(IOPCode.opc_pop);
		put(r1);
	}
	
	public void assemble_goto(int offset) {
		put(IOPCode.opc_goto);
		put(offset);
	}
	
	public void assemble_load(int r1, int value) {
		put(IOPCode.opc_load);
		put(r1);
		put(value);
	}
	
	public void assemble_debug(int action) {
		put(IOPCode.opc_debug);
		put(action);
	}
	
	public void assemble_if(int r1, int r2 ) {
		put(IOPCode.opc_if);
		put( r1 );
		put( r2 );
	}
	
	public void assemble_out(int r1) {
		put(IOPCode.opc_out);
		put(r1);
	}
	
	public  void assemble_end() {
		put(IOPCode.opc_end);
	}
	
	public void dump( int offset, int length ) {
		System.out.print("* MEMORY:" + offset + ":");
		for(int i=0; i<length; ++i)
			System.out.print( " " + storage[i+offset] );
	    System.out.print("\n");
	}
	
	public static int register_id(String name) {
		int id = 0;
		String tmp = name.trim().toUpperCase();
		
		if( tmp.equals("R0") )
			id = 0;
		if( tmp.equals("R1") )
			id=1;
		if( tmp.equals("R2") )
			id=2;
		if( tmp.equals("R3") )
			id=3;
		if( tmp.equals("R4") )
			id=4;
	
		return id;
	}
	
	
	//
	// LD  r1, r2
	public void assemble(String line) {
		StringTokenizer st = new StringTokenizer(line);
		System.out.println("assemble:" + line);
		
		while (st.hasMoreTokens() ) {
			String opcode = st.nextToken();
			String arguments = null;
			if( st.hasMoreElements()) {
				arguments = st.nextToken();
			}
			String args[] = new String[1];
			
			// System.out.println("arguments: " + arguments);
		
			if( arguments != null) {
			if( arguments.contains(",")  ) 
			    args = arguments.split(",");
			else 
				args[0] = arguments;
			}
			
			if(opcode.equals("ADD")) {
				int r1 = register_id( args[0] );
				int r2 = register_id( args[1] );
				
				assemble_add(r1, r2 );
			}
			if(opcode.equals("MULT")) {
				int r1 = register_id( args[0] );
				int r2 = register_id( args[1] );
				
				assemble_mult(r1, r2 );
			}
			
			if(opcode.equals("STORE")) {
				int r1 = register_id( args[0] );
				int loc = Integer.parseInt(args[1]);
				
				assemble_add(r1, loc );
			}
			
			if( opcode.equals( "FETCH" ) ) {
				int r1 = register_id(args[0]);
				int loc = Integer.parseInt(args[1]);
						
				assemble_fetch(r1, loc);
			}
			
			if( opcode.equals( "EQU" ) ) {
				int r1 = register_id(args[0]);
				int r2 = register_id(args[1]);
				int r3 = register_id(args[2]);
				
				assemble_equ(r1, r2, r3);
			}

			
			if( opcode.equals( "LOAD" ) ) {
				int r1 = register_id(args[0]);
				int value = Integer.parseInt(args[1]);
				
						
				assemble_load(r1, value);
			}
			
			if( opcode.equals( "PUSH" ) ) {
				int r1 = register_id(args[0]);
	
				assemble_push(r1);
			}
			if( opcode.equals( "POP" ) ) {
				int r1 = register_id(args[0]);
	
				assemble_pop(r1);
			}
			if( opcode.equals( "GOTO" ) ) {
				int loc = register_id(args[0]);
	
				assemble_goto(loc);
			}
		
			if( opcode.equals( "OUT" ) ) {
				int value = register_id(args[0]);
			
				assemble_out(value);
			}
			
			if( opcode.equals( "IF" ) ) {
				int r1 = register_id(args[0]);
				int r2 = register_id(args[1]);
				
				assemble_if(r1, r2);
			}
			
			if( opcode.equals( "DEBUG" ) ) {
				int value = Integer.parseInt(args[0]);
				
				assemble_debug(value);
			}
			
			if( opcode.equals( "END" ) ) {
				assemble_end();
			}
		 
		}
			
	}
	
}

