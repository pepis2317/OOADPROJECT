package views;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class TestView extends View implements ParameterView {
	private BorderPane bp;
	private Label testLabel;
	private String testParam;

	public TestView() {
		super();
	}

	@Override
	public void setParams(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		testParam = (String)params.get("testParam");
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		bp = new BorderPane();
		this.scene = new Scene(bp, 300, 300);
		
		testLabel = new Label();
	}

	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		bp.setCenter(testLabel);
	}

	@Override
	protected void style() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		testLabel.setText(testParam);
	}

}
