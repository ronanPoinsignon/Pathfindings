package appli;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author ronan
 *
 */
public class AppliFinding extends Application {

	public static void main(String args[]) {
		Application.launch(args);
		List<Test> liste = new ArrayList<>();
		liste.add(new Test(0, 0));
		liste.add(new Test(1, 0));
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/scene.fxml"));
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
	}
}

class Test {
	private int i, j;
	public Test(int i, int j) {
		this.i = i;
		this.j = j;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		if (i != other.i)
			return false;
		return true;
	}
	
	
}