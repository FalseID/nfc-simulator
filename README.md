# nfc-simulator
Simulator for various network function computation scenarios, made in conjunction with my thesis related to network function computation.
Project is in early stages.

Current features include:
JavaFX UI.
Graph viewing, drawing and editing.
Graph saving and loading to and from xml files.
Network function computation and feedback.

Basics:
To create a graph, click "Editing mode" to enter graph editing mode. Left-click will create a vertex, right-click will open a context-menu with additional options for creating network nodes such as sources and sinks. Edges can be made with a simple click and drag method on vertices or by selecting "Picking mode"
and while holding down shift, selecting two vertices and right clicking and choosing the preferred edge orientation.
You can zoom in and out with the scrollwheel. Click on "Tranforming mode" to simply view the graph and move around.

Known Bugs:
Jung framework has a bug related to edge creation. Clicking and dragging from a vertex to empty space will result in an exception and
a stuck edge. SImply click "Clear" under Edit menu to clear the current network.

Requires Java 8, uses JavaFX.
Uses Jung 2.1 framework for visualizing, drawing and interacting with graphs.
