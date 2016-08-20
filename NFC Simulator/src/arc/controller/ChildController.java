package arc.controller;

/**
 * Only purpose of this is to provide sub-controllers a reference to the main controller.
 * @author Janar
 *
 */
public interface ChildController {
	public void setParentController(MainController controller); 
}
