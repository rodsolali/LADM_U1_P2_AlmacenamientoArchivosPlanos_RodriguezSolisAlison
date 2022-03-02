package mx.tecnm.tepic.ladm_u1_p2_almacenamientoarchivosplanos_rodriguezsolisalison

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import mx.tecnm.tepic.ladm_u1_p2_almacenamientoarchivosplanos_rodriguezsolisalison.databinding.FragmentAgregarBinding
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class agregar : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var binding: FragmentAgregarBinding
    private val b get() = binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAgregarBinding.inflate(inflater,container,false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.btnagregar.setOnClickListener {
            agregar()
        }
    }
    fun agregar(){
        val filename = b.txtNombreP.text.toString()
        if(filename==""){
            Toast.makeText(context,"El nombre del producto no puede ser vacío",Toast.LENGTH_SHORT).show()
            return
        }
        val fileContents = b.txtPrecioP.text.toString();
            /*-----------Memoria interna----------------------
            openFileOutput(filename, Context.MODE_PRIVATE).use {
                        it.write(fileContents.toByteArray())
                        Toast.makeText(this, "Se ha guardado el archivo con éxito", Toast.LENGTH_SHORT).show()
                  }
                        */
            try {
                val externalStorageVolumes: Array<out File> =
                    ContextCompat.getExternalFilesDirs(requireContext(), null)
                val primaryExternalStorage = externalStorageVolumes[0]
                val file = File(primaryExternalStorage, filename) //crear archivo en memoria con ubicación y nombre
                file.writeText(fileContents)//Escribir información en archivo
                file.createNewFile() // crea el archivo
                Toast.makeText(context, "Se ha guardado el archivo con éxito", Toast.LENGTH_SHORT).show()
                b.txtNombreP.setText("")
                b.txtPrecioP.setText("")
            }
            catch (io: SecurityException) {
                Toast.makeText(context,"Ocurrio un error al guardar",Toast.LENGTH_SHORT).show()
            }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment agregar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            agregar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}