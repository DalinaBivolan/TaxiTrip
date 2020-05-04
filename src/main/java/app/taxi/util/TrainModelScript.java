package app.taxi.util;

import java.io.*;

import javax.swing.JTextArea;

public class TrainModelScript {
	
	public static final String scriptPath = "C:/Users/Dalina/Desktop/";

	public TrainModelScript(String scriptName, JTextArea text) throws IOException {
		String[] cmd = new String[2];
		cmd[0] = "python";
		cmd[1] = scriptPath + scriptName;
		System.out.println(cmd[1]);
		
		executeScript(cmd, text);
	}
	
	private void executeScript(String[] cmd, JTextArea text) {
		text.append("\nSe executa scriptul python...");
		text.update(text.getGraphics());
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		try {
			pr = rt.exec(cmd);
			text.append("\nScript executat...");
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
