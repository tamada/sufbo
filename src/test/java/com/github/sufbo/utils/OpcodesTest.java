package com.github.sufbo.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class OpcodesTest {
    @Test
    public void testOpcodes(){
        Opcodes opcodes = new Opcodes();

        assertThat(opcodes.of(0).name(), is("nop"));
        assertThat(opcodes.of(27).name(), is("iload_1"));
        assertThat(opcodes.of(50).opcode(), is(50));
        assertThat(opcodes.of(87).opcode(), is(87));

        assertThat(opcodes.of("imul").name(), is("imul"));
        assertThat(opcodes.of("invokeinterface").name(), is("invokeinterface"));
        assertThat(opcodes.of("invokedynamic").opcode(), is(186));
        assertThat(opcodes.of("if_icmpeq").opcode(), is(159));

        assertThat(opcodes.of(300).opcode(), is(-1));
        assertThat(opcodes.of("unknown").name(), is("not_found"));

        assertThat(opcodes.of(87).is(87), is(true));
        assertThat(opcodes.of(87).is(88), is(false));
        assertThat(opcodes.of(88).is("pop2"), is(true));
    }
}
