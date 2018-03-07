package com.newbig.word2xml;


import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws InterruptedException, AWTException {
		MSWordManager msWordManager = new MSWordManager();
		msWordManager.openDocument("G:\\HHMIndesign\\docx\\G160322.docx");
		Thread.sleep(5000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(1000);
		robot.mouseMove(850,137);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        Thread.sleep(1000);
//        robot.mouseMove(1214,212);
//        robot.keyPress(KeyEvent.BUTTON2_DOWN_MASK);
//        robot.keyRelease(KeyEvent.BUTTON2_DOWN_MASK);
        robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_BACK_SLASH);
		Thread.sleep(400);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_BACK_SLASH);
		Thread.sleep(10000);

		msWordManager.save("docx\\G160322.html");
		msWordManager.close();
	}


}