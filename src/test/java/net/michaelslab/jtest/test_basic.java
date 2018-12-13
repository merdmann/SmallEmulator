package net.michaelslab.jtest;

import java.io.BufferedReader;
import java.io.FileReader;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.Test;

import net.michaelslab.memory.*;
import net.michaelslab.operations.CPU;

public class test_basic {

	@Test(priority = 1)
	public void test_assemble_comment() {
		String line = ";; move ";
		
		net.michaelslab.memory.Memory mem = new net.michaelslab.memory.Memory( 10000, 1000);
		int wp0 = mem.wp;
	
		mem.assemble(line);
		Assert.assertEquals(wp0, mem.wp);
		 	
	}
	@Test(priority = 2)
	public void test_register_id() {
		net.michaelslab.memory.Memory mem = new net.michaelslab.memory.Memory( 10000, 1000);
		
		int r0 = net.michaelslab.memory.Memory.register_id("R0");
		int r1 = net.michaelslab.memory.Memory.register_id("R1");
		
		Assert.assertEquals( r0, 0);
		Assert.assertEquals( r1, 1);
	}
	
	@Test(priority = 3)
	public void test_assemble_ops() {
		net.michaelslab.memory.Memory mem = new net.michaelslab.memory.Memory( 10000, 1000);
		int wp0 = mem.wp;
		mem.assemble("LOAD R0,1");
		mem.assemble("LOAD R1,2");
		mem.assemble("LOAD R2,3");
		mem.assemble("LOAD R3,4");
		mem.assemble("LOAD R4,5");
		mem.assemble("OUT R0");
		mem.assemble("DEBUG 1");
		mem.assemble("END");
	
		Assert.assertEquals(mem.wp-wp0, 20);
	}
	
    @Test(priority = 4)
	public void test_register_oprations() {
		Memory mem = new net.michaelslab.memory.Memory( 10000, 1000);
		int [] regs = new int[5];
		
		CPU cpu = new net.michaelslab.operations.CPU(1, mem, 0, 2000, regs );
		
		mem.org(0);
		mem.assemble("LOAD R4,5");
		mem.assemble("LOAD r0,99");
		mem.assemble("OUT r0");
		mem.assemble("END");
		
		cpu.start(1, regs);

		Assert.assertEquals( cpu.IsStoppedState(), true);
		// since this starts the execution as a thread
		// it returns only with an end instruction.
		Assert.assertEquals( regs[0], 99);
		System.out.printf("%x %x %x %x %x", regs[0], regs[1], regs[2], regs[3], regs[4]);
			
		//Assert.assertEquals( regs[4], 5);
    }
	
    @Test(priority=5)
    public void test_all_regs() {
    	Memory mem = new Memory( 10000, 1000);
		int [] regs = new int[5];
		
		CPU cpu = new CPU(1, mem, 0, 2000, regs );
		
		mem.org(0);
		mem.assemble("LOAD R1,1");
		mem.assemble("LOAD R2,2");
		mem.assemble("LOAD R3,3");
		mem.assemble("LOAD R4,4");
		mem.assemble("END");
		
		cpu.start(1, regs);
		System.out.printf("Registers R0:x%x, R1:x%x, R2:x%x, R3:x%x R4:x%x\n", regs[0], regs[1], regs[2], regs[3], regs[4]);
		
		Assert.assertEquals(regs[0], 0);
		Assert.assertEquals(regs[1], 1);
		Assert.assertEquals(regs[2], 2);
		Assert.assertEquals(regs[3], 3);
	}
    
    @Test( priority=6)
	public void test_register_add_opperations() {
		Memory mem = new Memory( 10000, 1000);
		int [] regs = new int[5];  /* register probes */
		
		CPU cpu = new CPU(1, mem, 0, 2000, regs );
		
		mem.org(0);
		mem.assemble("LOAD R4,5");
		mem.assemble("LOAD R3,5");
		mem.assemble("ADD R4,R3");
		mem.assemble("OUT R4");
		mem.assemble("END");
		
		cpu.start(1, regs);

		System.out.printf("Registers, R0:0x%x, R1:0x%x, R2:0x%x, R3:0x%x, R4:0%x\n", regs[0], regs[1], regs[2], regs[3], regs[4]);
		Assert.assertEquals(regs[4], 10) ;
		Assert.assertEquals(regs[3], 5) ;
		// since this starts the execution as a thread
		// it returns only with an end instruction.
	}
	
}
