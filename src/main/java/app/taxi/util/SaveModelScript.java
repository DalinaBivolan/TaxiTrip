package app.taxi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextArea;

public class SaveModelScript {
	public static final String scriptPath = "C:/Users/Dalina/Desktop/";

	public SaveModelScript(String scriptName, JTextArea text, String savePath) throws IOException {
		String[] cmd = new String[2];
		cmd[0] = "python";
		cmd[1] = scriptPath + scriptName;
		System.out.println(cmd[1]);
		
		executeScript(cmd, text, savePath);
	}
	
	private void executeScript(String[] cmd, JTextArea text, String savePath) {
		text.append("\nSe executa scriptul python...");
		text.update(text.getGraphics());
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		try {
			pr = rt.exec(cmd);
			text.append("\nModelul va fi salvat la: \n");
			text.append(savePath + "\n");
			text.update(text.getGraphics());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";
		try {
			text.append("\nSe preia output-ul rularii..." + "\n");
			text.update(text.getGraphics());
			while((line = bfr.readLine()) != null){
				//display each output line from python script
				text.append(line + "\n");
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
