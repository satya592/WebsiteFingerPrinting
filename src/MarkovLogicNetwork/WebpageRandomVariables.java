package MarkovLogicNetwork;

import java.util.ArrayList;

public class WebpageRandomVariables {
	private String webpageName = "";
	private ArrayList<RandomVariableData> columnsListWebpage;
	
	public WebpageRandomVariables(String webpageName)
	{
		this.webpageName = webpageName;
		columnsListWebpage = new ArrayList<RandomVariableData>();
	}
	
	public WebpageRandomVariables(String webpageName, ArrayList<RandomVariableData> columnsListWebpage)
	{
		this.webpageName = webpageName;
		
		this.columnsListWebpage = new ArrayList<RandomVariableData>();
		this.columnsListWebpage = columnsListWebpage;
	}
	
	public String getWebpageName()
	{
		return webpageName;
	}
	
	public void setWebpageName(String webpageName)
	{
		this.webpageName = webpageName;
	}
	
	public ArrayList<RandomVariableData> getColumnsListWebpage()
	{
		return columnsListWebpage;
	}
	
	public void setColumnsListWebpage(ArrayList<RandomVariableData> columnsListWebpage)
	{
		this.columnsListWebpage = columnsListWebpage;
	}
}
