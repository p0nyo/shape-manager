
/*
 *  ============================================================================================
 *  A2.java : Complete this:
 *  YOUR UPI: ttse500s
 *  ============================================================================================
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import javax.swing.table.*;
import java.io.*;
import javax.swing.tree.*;

public class A2 extends JFrame {
	private NestedShape root = new NestedShape(Shape.DEFAULT_PANEL_WIDTH, Shape.DEFAULT_PANEL_HEIGHT);
	private NestedShape selectedShape = root;
	private CustomDataModel dataModel = new CustomDataModel();
	JComboBox<ShapeType> shapesComboBox;
	JButton addNodeButton, removeNodeButton;
	JTextField xTextField, yTextField, widthTextField, heightTextField;
	JTree tree;
	JTable shapesTable;

	public A2() {
		super("A2");
		JPanel mainPanel = setUpMainPanel();
		JPanel toolsPanel = setUpToolsPanel();
		add(mainPanel, BorderLayout.CENTER);
		add(toolsPanel, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public JPanel setUpMainPanel() {
		JPanel mainPanel = new JPanel();
		tree = new JTree(dataModel);
		// tree = new JTree();
		tree.addTreeSelectionListener(new SelectListener());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		JPanel treePanel = new JPanel(new BorderLayout());
		JScrollPane treeScrollpane = new JScrollPane(tree);
		JPanel treeButtonsPanel = new JPanel();
		addNodeButton = new JButton("Add Node");
		addNodeButton.addActionListener(new AddListener());
		removeNodeButton = new JButton("Remove Node");
		removeNodeButton.addActionListener(new RemoveListener());
		treeButtonsPanel.add(addNodeButton);
		treeButtonsPanel.add(removeNodeButton);
		treePanel.add(treeButtonsPanel, BorderLayout.NORTH);
		treePanel.add(treeScrollpane, BorderLayout.CENTER);

		shapesTable = new JTable(dataModel);
		// shapesTable = new JTable();
		treePanel.setPreferredSize(new Dimension(Shape.DEFAULT_PANEL_WIDTH / 2, Shape.DEFAULT_PANEL_HEIGHT));
		JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, new JScrollPane(shapesTable));
		mainSplitPane.setResizeWeight(0.5);
		mainSplitPane.setOneTouchExpandable(true);
		mainSplitPane.setContinuousLayout(true);
		mainPanel.add(mainSplitPane);
		return mainPanel;
	}

	public JPanel setUpToolsPanel() {
		shapesComboBox = new JComboBox<ShapeType>(new DefaultComboBoxModel<ShapeType>(ShapeType.values()));
		JPanel toolsPanel = new JPanel();
		toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.X_AXIS));
		toolsPanel.add(new JLabel(" Shape: ", JLabel.RIGHT));
		toolsPanel.add(shapesComboBox);
		toolsPanel.add(new JLabel("x: ", JLabel.RIGHT));
		xTextField = new JTextField("0", 10);
		xTextField.setToolTipText("Max:" + selectedShape.width);
		toolsPanel.add(xTextField);
		toolsPanel.add(new JLabel("y: ", JLabel.RIGHT));
		yTextField = new JTextField("0", 10);
		yTextField.setToolTipText("Max:" + selectedShape.height);
		toolsPanel.add(yTextField);
		toolsPanel.add(new JLabel("width: ", JLabel.RIGHT));
		widthTextField = new JTextField("" + selectedShape.width, 10);
		widthTextField.setToolTipText("Max:" + selectedShape.width);
		toolsPanel.add(widthTextField);
		toolsPanel.add(new JLabel("height: ", JLabel.RIGHT));
		heightTextField = new JTextField("" + selectedShape.height, 10);
		heightTextField.setToolTipText("Max:" + selectedShape.height);
		toolsPanel.add(heightTextField);
		xTextField.addActionListener(new XPositionListener());
		yTextField.addActionListener(new YPositionListener());
		widthTextField.addActionListener(new WidthListener());
		heightTextField.addActionListener(new HeightListener());

		return toolsPanel;
	}

	class CustomDataModel extends AbstractTableModel implements TreeModel {
		private ArrayList<TreeModelListener> treeModelListeners = new ArrayList<>();
		private String[] columnNames = { "type", "x", "y", "width", "height", "area" };

		// JTable Imeplementation starts here //

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return selectedShape.getSize();
		}

		public String getColumnName(int colIndex) {
			return columnNames[colIndex];
		}

		public Object getValueAt(int rowIndex, int colIndex) {
			Shape innerShape = selectedShape.getInnerShapeAt(rowIndex);
			switch (colIndex) {
				case 0:
					return innerShape.getClass().getName();
				case 1:
					return innerShape.x;
				case 2:
					return innerShape.y;
				case 3:
					return innerShape.width;
				case 4:
					return innerShape.height;
				case 5:
					return innerShape.getArea();
				default:
					return null;
			}
		}

		// JTable implementation stops here //

		// JTree implemenetation starts here //

		public NestedShape getRoot() {
			return root;
		}

		public boolean isLeaf(Object node) {
			if ((node instanceof Shape) && !(node instanceof NestedShape)) {
				return true;
			}
			return false;
		}

		public boolean isRoot(Shape selectedNode) {
			if (selectedNode == getRoot()) {
				return true;
			}
			return false;
		}

		public Object getChild(Object parent, int index) {
			if (parent instanceof NestedShape) {
				NestedShape nestedParent = (NestedShape) parent;
				if (index >= 0 && index < nestedParent.getSize()) {
					return nestedParent.getInnerShapeAt(index);
				}
				return null;
			}
			return parent;
		}

		public int getChildCount(Object parent) {
			if (parent instanceof NestedShape) {
				return ((NestedShape) parent).getSize();
			}
			return 0;
		}

		public int getIndexOfChild(Object parent, Object child) {
			if (parent instanceof NestedShape && child instanceof Shape) {
				return ((NestedShape) parent).indexOf((Shape) child);
			}
			return -1;
		}

		public void addTreeModelListener(TreeModelListener l) {
			treeModelListeners.add(l);
		}

		public void removeTreeModelListener(TreeModelListener l) {
			treeModelListeners.remove(l);
		}

		public void valueForPathChanged(TreePath path, Object newValue) {
		}

		protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {
			System.out.printf("Called fireTreeNodesInserted: path=%s, childIndices=%s, children=%s\n",
					Arrays.toString(path), Arrays.toString(childIndices), Arrays.toString(children));
			final TreeModelEvent event = new TreeModelEvent(source, path, childIndices, children);
			for (final TreeModelListener tml : treeModelListeners) {
				tml.treeNodesInserted(event);
			}
		}

		public void insertNodeInto(NestedShape selectedNode, ShapeType shapeType, int x, int y, int w, int h) {
			Shape newShape = selectedNode.createInnerShape(shapeType, x, y, w, h);
			int childIndex = selectedNode.getSize() - 1;
			int[] childIndices = { childIndex };
			Object[] children = { newShape };

			fireTreeNodesInserted(this, selectedNode.getPath(), childIndices, children);

			fireTableRowsInserted(childIndex, childIndex);
		}

		protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {
			System.out.printf("Called fireTreeNodesRemoved: path=%s, childIndices=%s, children=%s\n",
					Arrays.toString(path), Arrays.toString(childIndices), Arrays.toString(children));
			final TreeModelEvent event = new TreeModelEvent(source, path, childIndices, children);
			for (final TreeModelListener tml : treeModelListeners) {
				tml.treeNodesRemoved(event);
			}
		}

		public void removeNodeFromParent(Shape selectedNode) {
			NestedShape parentShape = selectedNode.getParent();

			int indexChild = parentShape.indexOf(selectedNode);

			parentShape.removeInnerShape(selectedNode);

			int[] childIndices = { indexChild };
			Object[] children = { selectedNode };

			fireTreeNodesRemoved(this, parentShape.getPath(), childIndices, children);

			fireTableRowsDeleted(indexChild, indexChild);
		}

		// JTree implementation stops here //
	}

	class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (tree.getLastSelectedPathComponent() != null) {
					if (tree.getLastSelectedPathComponent() instanceof NestedShape) {
						NestedShape lastSelected = (NestedShape) tree.getLastSelectedPathComponent();
						ShapeType shapeType = (ShapeType) shapesComboBox.getSelectedItem();
						int x = Integer.parseInt(xTextField.getText());
						int y = Integer.parseInt(yTextField.getText());
						int w = Integer.parseInt(widthTextField.getText());
						int h = Integer.parseInt(heightTextField.getText());
	
						dataModel.insertNodeInto(lastSelected, shapeType, x, y, w, h);
					}
					else {
						JOptionPane.showMessageDialog(null, "ERROR: Must select a NestedShape node.");
						System.out.println("ERROR: Must select a NestedShape node.");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "ERROR: No node selected.");
					System.out.println("ERROR: No node selected.");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: Invalid values!");
				System.out.println("ERROR: Invalid values!");
			}
		}
	}

	class RemoveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (tree.getLastSelectedPathComponent() != null) {
				Shape lastSelected = (Shape) tree.getLastSelectedPathComponent();
				if (lastSelected != root) {
					dataModel.removeNodeFromParent(lastSelected);
				}
				else {
					JOptionPane.showMessageDialog(null, "ERROR: Must not remove the root.");
					System.out.println( "ERROR: Must not remove the root.");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "ERROR: No node selected.");
				System.out.println("ERROR: No node selected.");
			}
		}
	}

	class SelectListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			if (tree.getLastSelectedPathComponent() != null) {
				if (tree.getLastSelectedPathComponent() instanceof NestedShape) {
					NestedShape selectedNestedShape = (NestedShape) tree.getLastSelectedPathComponent();
					selectedShape = selectedNestedShape;
					dataModel.fireTableDataChanged();
					tree.expandPath(tree.getSelectionPath());

					int width = selectedShape.width;
					int height = selectedShape.height;


					xTextField.setText("0");
					xTextField.setToolTipText("Max:"+width);

					yTextField.setText("0");
					yTextField.setToolTipText("Max:"+height);

					widthTextField.setText("" + width/2);
					widthTextField.setToolTipText("Max:"+width);

					heightTextField.setText("" + height/2);
					heightTextField.setToolTipText("Max:"+height);
				}
			}
		}
	}

	class XPositionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int xValue = Integer.parseInt(xTextField.getText());

				int maxWidth = selectedShape.width;

				if (xValue < 0 || xValue > maxWidth) {
					xTextField.setText("0");
				}
			} catch (NumberFormatException ex) {
				xTextField.setText("0");
			}
		}
	}

	class YPositionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int yValue = Integer.parseInt(yTextField.getText());

				int maxHeight = selectedShape.height;

				if (yValue < 0 || yValue > maxHeight) {
					yTextField.setText("0");
				}
			} catch (NumberFormatException ex) {
				yTextField.setText("0");
			}
		}
	}

	class WidthListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int maxWidth = selectedShape.width;
			try {
				int wValue = Integer.parseInt(widthTextField.getText());

				if (wValue < 0 || wValue > maxWidth) {
					widthTextField.setText(""+maxWidth/2);
				}
			} catch (NumberFormatException ex) {
				widthTextField.setText(""+maxWidth/2);
			}
		}
	}

	class HeightListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int maxHeight = selectedShape.height;
			try {
				int hValue = Integer.parseInt(heightTextField.getText());

				if (hValue < 0 || hValue > maxHeight) {
					heightTextField.setText(""+maxHeight/2);
				}
			} catch (NumberFormatException ex) {
				heightTextField.setText(""+maxHeight/2);
			}
		}
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new A2();
			}
		});
	}
}