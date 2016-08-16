package arc.visualization;

import java.awt.ItemSelectable;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;

import arc.core.IntermediaryVertex;
import arc.core.Sink;
import arc.core.Source;
import arc.core.Vertex;
import arc.core.Edge;

import com.google.common.base.Supplier;

import edu.uci.ics.jung.visualization.MultiLayerTransformer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.annotations.AnnotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.LabelEditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;



public class NetworkEditingMouse extends AbstractModalGraphMouse implements ModalGraphMouse, ItemSelectable {
	
	protected Supplier<IntermediaryVertex> vertexFactory;
	protected Supplier<Edge> edgeFactory;
	protected Supplier<Source> sourceFactory;
	protected Supplier<Sink> sinkFactory;
	protected EditingGraphMousePlugin<IntermediaryVertex, Edge> editingPlugin;
	protected LabelEditingGraphMousePlugin<Vertex, Edge> labelEditingPlugin;
	//Our custom popup menu plugin:
	protected PopupNetworkMenuMousePlugin popupEditingPlugin;
	protected AnnotatingGraphMousePlugin<Vertex, Edge> annotatingPlugin;
	protected MultiLayerTransformer basicTransformer;
	protected RenderContext<Vertex, Edge> renderContext;
	
	public NetworkEditingMouse(RenderContext renderContext, Supplier<IntermediaryVertex> vertexFactory, Supplier<Edge> edgeFactory, 
			Supplier<Source> sourceFactory, Supplier<Sink> sinkFactory) {
		this(renderContext, vertexFactory, edgeFactory,sourceFactory, sinkFactory, 1.1f, 1/1.1f);
	}

	public NetworkEditingMouse(RenderContext renderContext, Supplier<IntermediaryVertex> vertexFactory, Supplier<Edge> edgeFactory, 
			Supplier<Source> sourceFactory, Supplier<Sink> sinkFactory, float in, float out){
		super(in,out);
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
		this.sourceFactory = sourceFactory;
		this.sinkFactory = sinkFactory;
		this.renderContext = renderContext;
		this.basicTransformer = renderContext.getMultiLayerTransformer();
		loadPlugins();
		//setModeKeyListener(new ModeKeyAdapter(this));
		
	}
	@Override
	protected void loadPlugins(){
		pickingPlugin = new PickingGraphMousePlugin<Vertex, Edge>();
		animatedPickingPlugin = new AnimatedPickingGraphMousePlugin<Vertex, Edge>();
		translatingPlugin = new TranslatingGraphMousePlugin(InputEvent.BUTTON1_MASK);
		scalingPlugin = new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, in, out);
		rotatingPlugin = new RotatingGraphMousePlugin();
		shearingPlugin = new ShearingGraphMousePlugin();
		editingPlugin = new EditingGraphMousePlugin<IntermediaryVertex, Edge>(vertexFactory, edgeFactory);
		labelEditingPlugin = new LabelEditingGraphMousePlugin<Vertex, Edge>();
		annotatingPlugin = new AnnotatingGraphMousePlugin<Vertex, Edge>(renderContext);
		popupEditingPlugin = new PopupNetworkMenuMousePlugin(vertexFactory, edgeFactory, 
				sourceFactory, sinkFactory);
		add(scalingPlugin);
		setMode(Mode.EDITING);
	}
	
	 public void setMode(Mode mode) {
			if(this.mode != mode) {
				fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED,
						this.mode, ItemEvent.DESELECTED));
				this.mode = mode;
				if(mode == Mode.TRANSFORMING) {
					setTransformingMode();
				} else if(mode == Mode.PICKING) {
					setPickingMode();
				} else if(mode == Mode.EDITING) {
					setEditingMode();
				} else if(mode == Mode.ANNOTATING) {
					setAnnotatingMode();
				}
				if(modeBox != null) {
					modeBox.setSelectedItem(mode);
				}
				fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, mode, ItemEvent.SELECTED));
			}
		}
	 
	 @Override
    protected void setPickingMode() {
		remove(translatingPlugin);
		remove(rotatingPlugin);
		remove(shearingPlugin);
		remove(editingPlugin);
		remove(annotatingPlugin);
		add(pickingPlugin);
		add(animatedPickingPlugin);
		add(labelEditingPlugin);
		add(popupEditingPlugin);
	}

	@Override
    protected void setTransformingMode() {
		remove(pickingPlugin);
		remove(animatedPickingPlugin);
		remove(editingPlugin);
		remove(annotatingPlugin);
		add(translatingPlugin);
		add(rotatingPlugin);
		add(shearingPlugin);
		add(labelEditingPlugin);
		add(popupEditingPlugin);
	}

	protected void setEditingMode() {
		remove(pickingPlugin);
		remove(animatedPickingPlugin);
		remove(translatingPlugin);
		remove(rotatingPlugin);
		remove(shearingPlugin);
		remove(labelEditingPlugin);
		remove(annotatingPlugin);
		add(editingPlugin);
		add(popupEditingPlugin);
	}

	protected void setAnnotatingMode() {
		remove(pickingPlugin);
		remove(animatedPickingPlugin);
		remove(translatingPlugin);
		remove(rotatingPlugin);
		remove(shearingPlugin);
		remove(labelEditingPlugin);
		remove(editingPlugin);
		remove(popupEditingPlugin);
		add(annotatingPlugin);
	}
    
	/**
	 * @return the annotatingPlugin
	 */
	public AnnotatingGraphMousePlugin<Vertex, Edge> getAnnotatingPlugin() {
		return annotatingPlugin;
	}

	/**
	 * @return the editingPlugin
	 */
	public EditingGraphMousePlugin<IntermediaryVertex, Edge> getEditingPlugin() {
		return editingPlugin;
	}

	/**
	 * @return the labelEditingPlugin
	 */
	public LabelEditingGraphMousePlugin<Vertex, Edge> getLabelEditingPlugin() {
		return labelEditingPlugin;
	}

	/**
	 * @return the popupEditingPlugin
	 */
	public PopupNetworkMenuMousePlugin getPopupEditingPlugin() {
		return popupEditingPlugin;
	}
	
	
    
}