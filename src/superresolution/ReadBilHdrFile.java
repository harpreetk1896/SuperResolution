/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package superresolution;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

class ReadBilHdrFile
{
	private static File picfile;
	public static int Bands;
	private static int Rows;
	private static int Columns;

	public ReadBilHdrFile()
	{

	}

	public ReadBilHdrFile(File file)
	{
		String name = file.getName();
		this.picfile = file;
	}

	public void read(File Name)//read image file name
	{
		int index = 0;
		int b1 = 0;
		int row = 0;
		int col = 0;
		int x = 0;
		int c[];
		InputStream f1;
		char p[] = new char[45];
		String ban = new String();
		String row1 = new String();
		String col1 = new String();
		this.picfile = Name;
		String s[] = new String[30];
		try
		{
			f1 = new FileInputStream(Name + ".hdr");

			c = new int[11];
			for (int i = 0; i <= 43; i = i + 1)
			{
				p[i] = (char)f1.read();
			}
			f1.close();
		}
		catch (Exception e)
		{
		}

		for (int i = 6; i < 13; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			ban = ban + p[i];
		}
		b1 = Integer.parseInt(ban);
		this.Bands = b1;
		//System.out.println("ReadImage Band : " + b1);
		for (int i = 20; i <= 27; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			row1 = row1 + p[i];
		}
		row = Integer.parseInt(row1);
		//System.out.println("ReadImage Row : " + row);
		this.Rows = row;
		for (int i = 35; i <= 42; i = i + 1)
		{
			if (p[i] == '\n') break;
			if (p[i] == ' ') p[i] = '0';
			col1 = col1 + p[i];
		}
		//System.out.println("This is col1: " + col1);
		col = Integer.parseInt(col1);
		this.Columns = col;
		DemoPanel.bands = Bands;
		System.gc();
	}

	public int bands()//return bands
	{
		return Bands;
	}
	public int rows()//return rows
	{
		return Rows;
	}
	public int columns()//return columns
	{
		return Columns;
	}

	public File imagefile()//return imagefilename
	{
		return picfile;
	}
}