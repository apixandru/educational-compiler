package compiler;

import compiler.SeaCompiler;
import java.io.File;

import javax.swing.JFileChooser;
@SuppressWarnings("serial")
public class FileChooser extends JFileChooser{
	String fileName;
	public FileChooser() throws Exception {
		setCurrentDirectory(new File(new File(".").getCanonicalPath()));
	    int rVal = showOpenDialog(null);
	    if (rVal == JFileChooser.APPROVE_OPTION){
	    	fileName = getSelectedFile().toString();
	    	SeaCompiler.debugFile(fileName);
	    }
	}
	
	public String name(){
		return fileName;
	}
}
