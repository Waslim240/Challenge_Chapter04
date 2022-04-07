package waslim.binar.andlima.challengech04.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_dialog_edit.view.*
import kotlinx.android.synthetic.main.custom_dialog_save.*
import kotlinx.android.synthetic.main.custom_dialog_save.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_adapter_note.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import waslim.binar.andlima.challengech04.MainActivity
import waslim.binar.andlima.challengech04.R
import waslim.binar.andlima.challengech04.fragment.HomeFragment
import waslim.binar.andlima.challengech04.room.NoteTaking
import waslim.binar.andlima.challengech04.room.NoteTakingDatabase

class AdapterNoteTaking(val listNote : List<NoteTaking>) : RecyclerView.Adapter<AdapterNoteTaking.ViewHolder> () {

    private var dBase: NoteTakingDatabase? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterNoteTaking.ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_adapter_note, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: AdapterNoteTaking.ViewHolder, position: Int) {
        holder.itemView.tv_id.text = listNote[position].id.toString()
        holder.itemView.tv_judul.text = listNote[position].judul
        holder.itemView.tv_catatan.text = listNote[position].catatan

        // hapus
        holder.itemView.hapus.setOnClickListener {
            dBase = NoteTakingDatabase.getInstance(it.context)

            AlertDialog.Builder(it.context)
                .setTitle("Konfirmasi Hapus")
                .setMessage("Yakin Menghapus Data ?")

                .setPositiveButton("Ya" ) { dialogInterface: DialogInterface, i: Int ->
                    GlobalScope.async {
                        val result = dBase?.noteTakingDao()?.deleteNoteTaking(listNote[position])

                        (holder.itemView.context as MainActivity).runOnUiThread {
                            if (result != 0){
                                Toast.makeText(it.context, "Data ${listNote[position].judul} Berhasil Dihapus", Toast.LENGTH_LONG).show()
                                (holder.itemView.context as MainActivity).recreate()
                                (holder.itemView.context as MainActivity).overridePendingTransition(0,0)
                            } else{
                                Toast.makeText(it.context, "Data ${listNote[position].judul} Gagal Dihapus", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                } .setNegativeButton("Tidak") { dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(it.context, "Data ${listNote[position].judul} Tidak Jadi Dihapus", Toast.LENGTH_LONG).show()
                    dialogInterface.dismiss()
                }
                .show()
        }


        //edit

        holder.itemView.update.setOnClickListener {
            dBase = NoteTakingDatabase.getInstance(it.context)

            val pdh = LayoutInflater.from(it.context).inflate(R.layout.custom_dialog_edit, null, false)

            val alertD = AlertDialog.Builder(it.context)
                .setView(pdh)
                .create()

            pdh.masukan_judul_edit.setText(listNote[position].judul)
            pdh.masukan_catatan_edit.setText(listNote[position].catatan)

            pdh.btn_edit.setOnClickListener {
                val jdl = pdh.masukan_judul_edit.text.toString()
                val ctt = pdh.masukan_catatan_edit.text.toString()

                listNote[position].judul = jdl
                listNote[position].catatan = ctt

                GlobalScope.async {
                    val edt = dBase?.noteTakingDao()?.updateNoteTaking(listNote[position])

                    (pdh.context as MainActivity).runOnUiThread {
                        if (edt != 0){
                            Toast.makeText(it.context, "Data ${listNote[position].judul} Diedit", Toast.LENGTH_LONG).show()
                            (holder.itemView.context as MainActivity).recreate()
                            (holder.itemView.context as MainActivity).overridePendingTransition(0,0)
                        } else {
                            Toast.makeText(it.context, "Data ${listNote[position].judul} Gagal Diedit", Toast.LENGTH_LONG).show()
                        }

                    }
                }
            }
            alertD.show()
        }

    }

        override fun getItemCount(): Int {
            return listNote.size
    }
}