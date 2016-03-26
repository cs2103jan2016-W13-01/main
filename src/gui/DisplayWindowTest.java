/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author a0129845
 */
public class DisplayWindowTest {
    public static DisplayWindow DW;
    
    public DisplayWindowTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DW = new DisplayWindow();
    }
    
    @AfterClass
    public static void tearDownClass() {
        DW.setVisible(false);
        DW.dispose();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of clear method, of class DisplayWindow.
     */
    @Test
    public void testClear() {
        String content = "abcde" + "\n" + "cdefg";
        DW.getTaskDisplayArea().setText(content);
        DW.clear();
        content = DW.getTaskDisplayArea().getText();
        assertEquals(content, "");
    }

    /**
     * Test of displayStatusMsg method, of class DisplayWindow.
     */
    @Test
    public void testDisplayStatusMsg() {
        String status = "uploaded successfully";
        DW.getStatusField().setText(status);
        String exp = DW.getStatusField().getText();
        assertEquals(status, exp);
    }

    /**
     * Test of displayTaskList method, of class DisplayWindow.
     */
    @Test
    public void testDisplayTaskList() {
        ArrayList<String> tasks = new ArrayList<String>();
        tasks.add("first line");
        tasks.add("second line");
        DW.displayTaskList(tasks);
        String exp = ("1. first line" + "\r\n" + "2. second line" + "\r\n");
        String task = DW.getTaskDisplayArea().getText();
        assertEquals(exp, task);
    }
    
}
