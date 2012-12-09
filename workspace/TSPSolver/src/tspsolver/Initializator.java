package tspsolver;

import tspsolver.model.ProgramState;
import tspsolver.model.SolvedProblems;
import tspsolver.model.interfaces.IModelChangeListener;

public class Initializator {
	
	
	private ProgramState programState;
	private GuiActionsListener actionsListener;
	
	public Initializator(IModelChangeListener el){
		if(el == null){
			throw new IllegalArgumentException("change listener can't be null!");
		}
		
		programState = new ProgramState(el);
		actionsListener = new GuiActionsListener(programState);
	}


	public GuiActionsListener getActionsListener() {
		return actionsListener;
	}
	
	public SolvedProblems getSolvedProblems(){
		return programState.getSolvedProblems();
	}


}
