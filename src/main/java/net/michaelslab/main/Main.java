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
package net.michaelslab.main;

import java.io.*;

import net.michaelslab.memory.Memory;
import net.michaelslab.operations.CPU;

public class Main {

	public static void main(String args[]) {
				File file = new File(args[0]);
				
				System.out.println("Assembling file:" + args[0] + "\n");
				
				Memory mem = new Memory( 10000, 1000);
				
				try (BufferedReader br = new BufferedReader(new FileReader(file))) {
					String line;
					
					try {
						while ((line = br.readLine()) != null) {
							if( !line.startsWith(";;")) {
									mem.assemble(line);
							}
						}
					}
					catch( Exception e) {
						System.out.println(e);
					}
					
				}
				catch(FileNotFoundException ex) {
					System.out.println("Exception:" + ex );
					
				}
				catch(IOException ex) {
					System.out.println(ex);
				}
				
				
/*				This is some test code 
				mem.assemble("LOAD R0,1");
				mem.assemble("LOAD R1,2");
				mem.assemble("LOAD R2,3");
				mem.assemble("LOAD R3,4");
				mem.assemble("LOAD R4,5");
				mem.assemble("OUT R0");
				mem.assemble("DEBUG 1");
				mem.assemble("END");
*/			
				int [] regs = new int[5];
				
				CPU cpu = new CPU (0, mem, 0, 1000, regs);
				cpu.start(1, regs);
				cpu.start(2, regs);
				cpu.start(3, regs);
				cpu.start(4, regs);
				
	} /* main */

}
