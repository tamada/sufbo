package com.github.sufbo.entities.java;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.sufbo.entities.java.Bytecode;

public class BytecodeTest {
    @Test
    public void testBytecode(){
        Bytecode bytecode = new Bytecode(new byte[] { (byte)0xca, (byte)0xfe, (byte)0xba, (byte)0xbe, });

        assertThat(bytecode.length(), is(4));
        assertThat(bytecode.toString(), is("cafebabe"));
    }
}
