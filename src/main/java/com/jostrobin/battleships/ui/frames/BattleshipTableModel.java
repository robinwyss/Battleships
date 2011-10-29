package com.jostrobin.battleships.ui.frames;

import javax.swing.table.AbstractTableModel;

public class BattleshipTableModel extends AbstractTableModel
{
	private String[] columnNames;
	
	private Object[][] rowData;
	
	public BattleshipTableModel(String[] columnNames, Object[][] rowData)
	{
		this.columnNames = columnNames;
		this.rowData = rowData;
	}

	@Override
	public int getRowCount()
	{
		return rowData.length;
	}

	@Override
	public int getColumnCount()
	{
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return rowData[rowIndex][columnIndex];
	}

	@Override
	public String getColumnName(int col)
	{
		return columnNames[col].toString();
	}

	@Override
	public boolean isCellEditable(int row, int col)
	{
		return false;
	}

	@Override
	public void setValueAt(Object value, int row, int col)
	{
		rowData[row][col] = value;
		fireTableCellUpdated(row, col);
	}
	
	public void addRow(Object[] data)
	{
		Object[][] temp = new Object[rowData.length+1][columnNames.length];
		int i = 0;
		for (Object[] row : rowData)
		{
			temp[i++] = row;
		}
		temp[temp.length] = data;
		fireTableDataChanged();
	}
}
