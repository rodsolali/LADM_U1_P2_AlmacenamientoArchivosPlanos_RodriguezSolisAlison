package mx.tecnm.tepic.ladm_u1_p2_almacenamientoarchivosplanos_rodriguezsolisalison

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder<T>(itemView: View):RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: ModeloJoya, position:Int)
}