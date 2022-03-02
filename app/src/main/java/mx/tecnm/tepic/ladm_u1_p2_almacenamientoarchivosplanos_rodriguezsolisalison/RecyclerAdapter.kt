package mx.tecnm.tepic.ladm_u1_p2_almacenamientoarchivosplanos_rodriguezsolisalison

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.tecnm.tepic.ladm_u1_p2_almacenamientoarchivosplanos_rodriguezsolisalison.databinding.JoyaRowBinding
import java.lang.IllegalArgumentException

class RecyclerAdapter (private val context: Context, val listaJoya:List<ModeloJoya>, private val itemClickListener:onClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return JoyaViewHolder(LayoutInflater.from(context).inflate(R.layout.joya_row,parent,false))
    }
    interface onClickListener{
        fun onItemLongClick(
        ):Boolean
        fun onClick( nombre: String,precio:Double, itemView: View, position: Int):Boolean
    }

    //Para cada informacion
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is RecyclerAdapter.JoyaViewHolder -> holder.bind(listaJoya[position],position)
            else -> throw IllegalArgumentException("No se paso el viewholder")
        }
    }

    override fun getItemCount(): Int = listaJoya.size
    //como tratar la informacion
    inner class JoyaViewHolder(itemView: View): BaseViewHolder<ModeloJoya>(itemView){
        val b = JoyaRowBinding.bind(itemView)
        override fun bind(item: ModeloJoya, position: Int) {
            itemView.setOnClickListener { itemClickListener.onClick(item.nombre,item.precio,itemView,position) }
            b.imagen.setBackgroundResource(item.img)
            b.txtRowProducto.text = item.nombre
            b.txtRowPrecio.text = item.precio.toString()
        }
    }
}