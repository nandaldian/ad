using Gtk;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;

namespace CArticulo
{
    public partial class ArticuloWindow : Gtk.Window
    {
        public ArticuloWindow(Articulo articulo) : base(Gtk.WindowType.Toplevel){
            this.Build();

            entryNombre.Text = articulo.Nombre ?? "";
			spinButtonPrecio.Value = (double)articulo.Precio;
			
			saveAction.Activated += delegate {
				Console.WriteLine("saveAction.Activated");
				articulo.Nombre = entryNombre.Text;
				articulo.Precio = (decimal)spinButtonPrecio.Value;
				ArticuloDao.Save(articulo);

                Destroy();

			};

		}

		
	}


}
