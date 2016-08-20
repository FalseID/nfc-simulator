package arc.ui.visualization;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import arc.core.Edge;
import arc.core.IntermediaryVertex;
import arc.core.Sink;
import arc.core.Source;
import arc.core.Vertex;

import com.google.common.base.Supplier;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;

/**
 * Custom popup menu for vertices. Added menus for the creation of source and sink nodes.
 * @author Janar
 *
 */
public class PopupNetworkMenuMousePlugin extends AbstractPopupGraphMousePlugin {

	 	protected Supplier<IntermediaryVertex> vertexFactory;
	    protected Supplier<Edge> edgeFactory;
	    protected Supplier<Source> sourceFactory;
	    protected Supplier<Sink> sinkFactory;

	    public PopupNetworkMenuMousePlugin(Supplier<IntermediaryVertex> vertexFactory, Supplier<Edge> edgeFactory, 
	    		Supplier<Source> sourceFactory, Supplier<Sink> sinkFactory) {
	        this.vertexFactory = vertexFactory;
	        this.edgeFactory = edgeFactory;
	        this.sourceFactory = sourceFactory;
	        this.sinkFactory = sinkFactory;
	    }
	    
		@SuppressWarnings({ "unchecked", "serial" })
		protected void handlePopup(MouseEvent e) {
	        final VisualizationViewer<Vertex, Edge> vv =
	            (VisualizationViewer<Vertex, Edge>)e.getSource();
	        final Layout<Vertex, Edge> layout = vv.getGraphLayout();
	        final Graph<Vertex, Edge> graph = layout.getGraph();
	        final Point2D p = e.getPoint();
	        GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport();
	        if(pickSupport != null) {
	            
	            final Vertex vertex = pickSupport.getVertex(layout, p.getX(), p.getY());
	            final Edge edge = pickSupport.getEdge(layout, p.getX(), p.getY());
	            final PickedState<Vertex> pickedVertexState = vv.getPickedVertexState();
	            final PickedState<Edge> pickedEdgeState = vv.getPickedEdgeState();
	            
	            JPopupMenu popup = new JPopupMenu();
	            if(vertex != null) {
	            	Set<Vertex> picked = pickedVertexState.getPicked();
	            	if(picked.size() > 0) {
            			JMenu directedMenu = new JMenu("Create Directed Edge");
            			popup.add(directedMenu);
            			for(final Vertex other : picked) {
            				directedMenu.add(new AbstractAction("["+other+","+vertex+"]") {
            					public void actionPerformed(ActionEvent e) {
            						graph.addEdge(edgeFactory.get(),
            								other, vertex, EdgeType.DIRECTED);
            						vv.repaint();
            					}
            				});
            			}
	                }
	                popup.add(new AbstractAction("Delete Vertex") {
	                    public void actionPerformed(ActionEvent e) {
	                        pickedVertexState.pick(vertex, false);
	                        graph.removeVertex(vertex);
	                        vv.repaint();
	                    }});
	            } else if(edge != null) {
	                popup.add(new AbstractAction("Delete Edge") {
	                    public void actionPerformed(ActionEvent e) {
	                        pickedEdgeState.pick(edge, false);
	                        graph.removeEdge(edge);
	                        vv.repaint();
	                    }});
	            } else {
	                popup.add(new AbstractAction("Create Vertex") {
	                    public void actionPerformed(ActionEvent e) {
	                        Vertex newVertex = (Vertex) vertexFactory.get();
	                        graph.addVertex(newVertex);
	                        layout.setLocation(newVertex, vv.getRenderContext().getMultiLayerTransformer().inverseTransform(p));
	                        vv.repaint();
	                    }
	                });
	                popup.add(new AbstractAction("Create Source") {
	                    public void actionPerformed(ActionEvent e) {
	                        Vertex newVertex = (Vertex) sourceFactory.get();
	                        graph.addVertex(newVertex);
	                        layout.setLocation(newVertex, vv.getRenderContext().getMultiLayerTransformer().inverseTransform(p));
	                        vv.repaint();
	                    }
	                });
	                popup.add(new AbstractAction("Create Sink") {
	                    public void actionPerformed(ActionEvent e) {
	                        Vertex newVertex = (Vertex) sinkFactory.get();
	                        graph.addVertex(newVertex);
	                        layout.setLocation(newVertex, vv.getRenderContext().getMultiLayerTransformer().inverseTransform(p));
	                        vv.repaint();
	                    }
	                });
	                
	            }
	            if(popup.getComponentCount() > 0) {
	                popup.show(vv, e.getX(), e.getY());
	            }
	        }
	  }
}

