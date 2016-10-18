package cn.sgr.zmr.com.sgr;

import org.junit.Test;

import cn.sgr.zmr.com.sgr.Utils.util.Utils;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void IsNum() throws Exception {
        boolean result = Utils.isNumber("-1.0");
        System.out.println("result"+result);
        assertEquals(true, result);
    }

    @Test
    public void IsWhole() throws Exception {
        float i= (float) 2.1;
        System.out.println("舍掉小数取整:Math.floor(2)=" + (int)Math.floor(i));

    }




}