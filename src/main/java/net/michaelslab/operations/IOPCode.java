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

public interface IOPCode {  // opperartion codes
	int opc_add 	= 1;	// r1 = r1 + r2
	int opc_mult 	= 2;	// r1 = r1 * r2
	int opc_store 	= 3;	// r1 --> loc(argument)
	int opc_fetch 	= 4;
	int opc_equ 	= 5;
	int opc_push 	= 6;
	int opc_pop 	= 7;
	int opc_goto 	= 8;
	int opc_load 	= 9;
	int opc_out 	= 10;
	int opc_if 		= 11;
	int opc_debug 	= 12;
	int opc_end 	= 13;
}