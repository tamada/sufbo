package com.github.sufbo.entities;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ByteArrayTest {
    @Test
    public void testBytecode(){
        ByteArray bytecode = new ByteArray(new byte[] { (byte)0xca, (byte)0xfe, (byte)0xba, (byte)0xbe, });

        assertThat(bytecode.size(), is(4));
        assertThat(bytecode.toString(), is("cafebabe"));
    }

    @Test
    public void testParse(){
        ByteArray array = ByteArray.parse("cafebabe");
        Integer[] expected = new Integer[] { 0xca, 0xfe, 0xba, 0xbe };
        Integer[] actual = array.stream().mapToObj(Integer::new).toArray(size -> new Integer[size]);

        assertThat(array.size(), is(4));
        assertThat(actual, is(arrayContaining(expected)));
    }
}
