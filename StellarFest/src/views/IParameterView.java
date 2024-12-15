package views;

import java.util.HashMap;

public interface IParameterView {
//	Interface implemented for views that require parameters to load
	
	public void setParams(HashMap<String, Object> params);
}
