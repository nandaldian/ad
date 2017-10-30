﻿using Gtk;
using MySql.Data.MySqlClient;
using System;
using System.Data;
using Serpis.Ad;
using CCategoria;

public partial class MainWindow : Gtk.Window
{
    
    public MainWindow() : base(Gtk.WindowType.Toplevel)
    {
        Build();
        Title = "Categoria";
        deleteAction.Sensitive = false;
        editAction.Sensitive = false;

        //AÑADIMOS COLUMNAS AL TREEVIEW
        treeView.AppendColumn("id", new CellRendererText(), "text", 0);
        treeView.AppendColumn("nombre", new CellRendererText(), "text", 1);
        ListStore listStore = new ListStore(typeof(string), typeof(string));
        /*listStore.AppendValues("1","cat. 1");
        listStore.AppendValues("2","cat. 2");
        */
        treeView.Model = listStore;

        // NOS CONECTAMOS A LA BASE DE DATOS
		string connectionString = "server = localhost;database = dbprueba;user = root;password=sistemas";
        App.Instance.Connection = new MySqlConnection(connectionString);
        App.Instance.Connection.Open();


        fillListStore(listStore);

        treeView.Selection.Changed += delegate {
            bool hasSelected = treeView.Selection.CountSelectedRows() > 0;
            deleteAction.Sensitive = hasSelected;
            editAction.Sensitive = hasSelected;
        };


       

        newAction.Activated += delegate
        {
            Categoria categoria = new Categoria();
            new CategoriaWindow(categoria);
        };

        editAction.Activated += delegate {
            object id = getId();
            Categoria categoria = CategoriaDao.Load(id);
            new CategoriaWindow(categoria);
        };


        refreshAction.Activated += delegate {
			listStore.Clear();

            fillListStore(listStore);
        };

        deleteAction.Activated += delegate {
           if (WindowHelper.Confirm(this, "¿Quieres eliminar el registro?")){
                object id = getId();
                CategoriaDao.Delete(id);
				
            }
                
            
        };


        		
    }

    private object getId(){
		TreeIter treeIter;
		treeView.Selection.GetSelected(out treeIter);
        return treeView.Model.GetValue(treeIter, 0);
    }

    //METODO LEER Y RELLENAR LA TABLA
    private void fillListStore(ListStore listStore){
		IDbCommand dbCommand = App.Instance.Connection.CreateCommand();
		dbCommand.CommandText = "select * from categoria order by id";
		IDataReader dataReader = dbCommand.ExecuteReader();

		while (dataReader.Read())
		{
			listStore.AppendValues(dataReader["id"].ToString(), dataReader["nombre"]);
		}
		dataReader.Close();

	}


    protected void OnDeleteEvent(object sender, DeleteEventArgs a)
    {
        App.Instance.Connection.Close();

        Application.Quit();
        a.RetVal = true;
    }
}