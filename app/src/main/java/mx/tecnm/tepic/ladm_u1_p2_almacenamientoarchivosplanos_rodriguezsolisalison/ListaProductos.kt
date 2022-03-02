package mx.tecnm.tepic.ladm_u1_p2_almacenamientoarchivosplanos_rodriguezsolisalison

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import mx.tecnm.tepic.ladm_u1_p2_almacenamientoarchivosplanos_rodriguezsolisalison.databinding.FragmentListaProductosBinding
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListaProductos.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaProductos : Fragment(),RecyclerAdapter.onClickListener {
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


    private lateinit var _binding: FragmentListaProductosBinding
    private val b get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListaProductosBinding.inflate(inflater, container, false)
        return b.root
    }

    lateinit var listaJoya:MutableList<ModeloJoya>
    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        listaJoya = mutableListOf<ModeloJoya>()
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        b.listaJoyas.layoutManager = LinearLayoutManager(activity?.applicationContext!!)
        b.listaJoyas.addItemDecoration(
            DividerItemDecoration(activity?.applicationContext!!,
                DividerItemDecoration.VERTICAL)
        )
        val externalStorageVolumes: Array<out File> =
            ContextCompat.getExternalFilesDirs(requireContext(), null)
        val primaryExternalStorage = externalStorageVolumes[0]
        val filesE: Array<File> = File(primaryExternalStorage.absoluteFile.toURI()).listFiles()
        filesE.forEach {
            val externo = File(primaryExternalStorage,it.name)
            listaJoya.add(ModeloJoya(it.name,externo.readText().toDouble()))
        }
        b.listaJoyas.adapter = RecyclerAdapter(activity?.applicationContext!!,listaJoya,this)
    }

    private fun RecargarRecycler(){
        b.listaJoyas.adapter = RecyclerAdapter(activity?.applicationContext!!,listaJoya,this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListaProductos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListaProductos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemLongClick(): Boolean {
        return true
    }

    override fun onClick(nombre: String, precio: Double, itemView: View, position: Int): Boolean {
        val menu = PopupMenu(context,itemView)
        val builder = AlertDialog.Builder(context)
        menu.menu.add("Borrar")
        menu.menu.add("Editar")
        menu.setOnMenuItemClickListener {
            menu.dismiss()
            if (it.title.equals("Borrar")) {
                builder.setTitle("Advertencia")
                builder.setMessage("¿Está seguro de borrar ${nombre}?")
                builder.setPositiveButton("Si"){ d,w->
                    val externalStorageVolumes: Array<out File> =
                        ContextCompat.getExternalFilesDirs(requireContext(), null)
                    val primaryExternalStorage = externalStorageVolumes[0]
                    val file = File(primaryExternalStorage, nombre)
                    file.delete()
                    listaJoya.removeAt(position)
                    RecargarRecycler()
                }
                builder.setNegativeButton("No"){ d,w->
                    d.cancel()
                }
            }
            else if (it.title.equals("Editar")) {
                val bundle = Bundle()
                bundle.putString("Nombre",nombre)
                val fragment2 = EditarProductos()
                fragment2.arguments = bundle
                view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_listaProductos_to_editarProductos2) };
                menu.dismiss()
            }
            builder.show()
            true
        }
        menu.show()
        return true
    }

}