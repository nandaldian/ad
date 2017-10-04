using Gtk;
using MySql.Data.MySqlClient;
using System;
using System.Data;


public partial class MainWindow : Gtk.Window
//TODO Una clase singleton para la conexión
{
	public MainWindow() : base(Gtk.WindowType.Toplevel)
	{
		Build();
		treeView.AppendColumn("id", new CellRendererText(), "text", 0);
		treeView.AppendColumn("nombre", new CellRendererText(), "text", 1);
		ListStore listStore = new ListStore(typeof(long), typeof(string));
		treeView.Model = listStore;
		String command;
		String id;
		String nombre;


		IDbConnection dbConnection = new MySqlConnection(
			"Database=dbprueba;User Id=root;Password=sistemas"
			);
		IDbCommand cmd = dbConnection.CreateCommand();

		try
		{
			dbConnection.Open();
		}
		catch (Exception)
		{
			Console.Clear();
			//TODO Incluir los catch en ventanas a parte
			//MessageDialog msgdiag = new MessageDialog (this, DialogFlags.Modal, MessageType.Error, ButtonsType.Close, "Error al conectar a la base de datos");

		}
		command = "select * from categoria";
		cmd.CommandText = command;
		IDataReader reader = cmd.ExecuteReader();
		Boolean empty = true;

		while (reader.Read())
		{
			id = reader[0].ToString();
			nombre = reader[1].ToString();
			//Console.WriteLine (id + " " + nombre);
			listStore.AppendValues(Int64.Parse(id), nombre);
			empty = false;
		}
		reader.Close();

		if (empty)
		{
			//TODO Incluir los catch en ventanas a parte
			//MessageDialog msgdiag = new MessageDialog 
			//(this, DialogFlags.Modal, MessageType.Error, ButtonsType.Close, 
			//"La base de datos está vacía");
		}




	}

	protected void OnDeleteEvent(object sender, DeleteEventArgs a)
	{
		Application.Quit();
		a.RetVal = true;
	}
	protected void loadValues(IDbConnection dbConnection, ListStore listStore)
	{

	}


}
